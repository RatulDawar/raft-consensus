package org.butterchicken.raft.consensus;

import io.grpc.ManagedChannel;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.Getter;
import lombok.Setter;
import org.butterchicken.raft.Utils.Config;
import org.butterchicken.raft.Utils.RaftServerConfig;
import org.butterchicken.raft.cluster.ClusterNode;
import org.butterchicken.raft.consensus.enums.ServerState;
import org.butterchicken.raft.consensus.persist.RaftPersistantState;
import org.butterchicken.raft.consensus.request.HeartbeatRequest;
import org.butterchicken.raft.statemachine.StateMachine;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.HeartbeatImpl;
import service.RequestVoteImpl;

@Getter
@Setter
public class RaftServer {

    private static final Logger logger = LogManager.getLogger(RaftServer.class);

    Integer id;
    String address;

    Integer heartbeatMs;
    StateMachine stateMachine;
    ServerState serverState;
    List<ClusterNode> cluster;
    Integer clusterIndex;
    Integer port;
    RaftPersistantState raftPersistantState;
    ReentrantLock lock = new ReentrantLock();

    static RaftServer  instance;
    private ScheduledExecutorService heartbeatsThread = new ScheduledThreadPoolExecutor(1);
    ElectionManager electionManager;

    private RaftServer() throws  RuntimeException{
        try {
            this.cluster = Config.loadClusterConfig();
            this.heartbeatMs = 3000;
            // load configs from file
            RaftServerConfig raftServerConfig = Config.loadServerConfig();
            this.port = raftServerConfig.getPort();
            this.id = raftServerConfig.getId();
            this.clusterIndex = findClusterIndex();
        }catch (IOException | IllegalStateException exception){
            throw new RuntimeException("Configuration error  : " + exception.getMessage());
        }
    }


    public void start() throws IOException, InterruptedException {

        synchronized(this) {
            // initial state of raft server is follower
            this.serverState = ServerState.FOLLOWER;
        }
        // loading persistant state from disk or initialising it
        this.raftPersistantState = new RaftPersistantState();

        Server server = ServerBuilder.forPort(this.port).addService(new RequestVoteImpl()).addService(new HeartbeatImpl()).build();
        server.start();

        electionManager = ElectionManager.getInstance();

    }


    public void changeStateToCandidate(){
        lock.lock();
        this.serverState = ServerState.CANDIDATE;
        heartbeatsThread.shutdownNow();
        lock.unlock();
    }
    public void changeStateToLeader(){
        lock.lock();
        this.serverState = ServerState.LEADER;
        startHeartBeatThread();
        lock.unlock();
    }
    public void changeStateToFollower(){
        lock.lock();
        this.serverState = ServerState.FOLLOWER;
        heartbeatsThread.shutdownNow();
        lock.unlock();

    }

    private void startHeartBeatThread(){
        this.heartbeatsThread = Executors.newScheduledThreadPool(1);
        Runnable heartbeatThread = () -> {
            cluster.stream()
                    .filter(clusterNode -> !clusterNode.getId().equals(getId()))
                    .forEach(clusterNode -> {
                        ManagedChannel channel = null;
                        try {
                            logger.debug("Sending heartbeat to " + clusterNode.getHost() + ":" +clusterNode.getPort());
                            RPCAdapter.sendHeartbeat(clusterNode,new HeartbeatRequest(this.getId(), this.getCurrentTerm()));
                        } catch (Exception e) {
                            logger.error(e.getMessage());
                        } finally {
                            if(channel != null) {
                                channel.shutdown();
                            }

                        }
                    });
        };

        heartbeatsThread.scheduleAtFixedRate(heartbeatThread,0,this.heartbeatMs,TimeUnit.MILLISECONDS);
    }


    public void handleIncomingHeartbeat(HeartbeatRequest heartbeatRequest){
        logger.trace("Received heartbeat from node " + heartbeatRequest.getSenderId() + " on node " + this.getId());
        updateTerm(heartbeatRequest.getTerm());
        ElectionManager.getInstance().resetElectionTimeout();
    }


    public static RaftServer getInstance(){
        if(instance == null){
            instance = new RaftServer();
        }
        return instance;
    }

    private Integer findClusterIndex() {
        for(int i = 0;i<this.cluster.size();i++){
            if(this.getId().equals(this.cluster.get(i).getId())){
                return i;
            }
        }

        throw new IllegalStateException("Provided Id not found in the cluster");
    }

    public Integer getCurrentTerm(){
        return raftPersistantState.getCurrentTerm();
    }
    public Boolean updateTerm(Integer term){
        Boolean termUpdated = Boolean.FALSE;
        if(term > raftPersistantState.getCurrentTerm()){
            raftPersistantState.setCurrentTerm(term);
            this.changeStateToFollower();
            setVotedFor(null);
            termUpdated = Boolean.TRUE;
            logger.trace("Node " + this.getId()+ " transitioned to follower");
        }
        return termUpdated;
    }
    public Integer getVotedFor(){
        return raftPersistantState.getVotedFor();
    }

    public void setVotedFor(Integer votedFor){
        raftPersistantState.setVotedFor(votedFor);
    }
    public void incrementTerm(){
        this.raftPersistantState.setCurrentTerm(this.getCurrentTerm() + 1);
    }


}
