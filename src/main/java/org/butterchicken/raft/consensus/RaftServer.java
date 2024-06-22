package org.butterchicken.raft.consensus;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.butterchicken.raft.Utils.Config;
import org.butterchicken.raft.Utils.RaftServerConfig;
import org.butterchicken.raft.cluster.ClusterNode;
import org.butterchicken.raft.consensus.enums.ServerState;
import org.butterchicken.raft.consensus.persist.RaftPersistantState;
import org.butterchicken.raft.consensus.request.HeartbeatRequest;
import org.butterchicken.raft.statemachine.StateMachine;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;



import service.HeartbeatImpl;
import service.LogReplicationImpl;
import service.RequestVoteImpl;
import org.butterchicken.raft.consensus.LogManager;

@Getter
@Setter
@Log4j2
public class RaftServer {


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
            this.heartbeatMs = 8000;
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
        this.raftPersistantState = RaftPersistantState.getInstance();

        Server server = ServerBuilder.forPort(this.port).addService(new RequestVoteImpl()).addService(new HeartbeatImpl()).addService(new LogReplicationImpl()).build();
        server.start();

        electionManager = ElectionManager.getInstance();

    }


    public void changeStateToCandidate(){
        this.serverState = ServerState.CANDIDATE;
        heartbeatsThread.shutdownNow();
    }
    public void changeStateToLeader() {
        this.serverState = ServerState.LEADER;
        LogManager.getInstance().resetLogStates();
        log.trace("New leader has been elected, node " + this.getId());
        //this.raftPersistantState.appendToLog(new LogEntry(RaftServer.getInstance().getCurrentTerm(), new byte[1])); TODO a sync message for states
        startHeartBeatThread();
    }
    public void changeStateToFollower(){
        this.serverState = ServerState.FOLLOWER;
        heartbeatsThread.shutdownNow();
    }

    private void startHeartBeatThread(){
        this.heartbeatsThread = Executors.newScheduledThreadPool(1);
        Runnable heartbeatThread = () -> {
            cluster.stream()
                    .filter(clusterNode -> !clusterNode.getId().equals(getId()))
                    .forEach(clusterNode -> {
                            log.trace("Sending heartbeat to " + clusterNode.getHost() + ":" +clusterNode.getPort());
                            RPCAdapter.sendHeartbeat(clusterNode,new HeartbeatRequest(this.getId(), this.getCurrentTerm()));
                        try {

                            apply("SET x = 1");
                        } catch (RuntimeException e) {
                            log.error("Test replication command failure :" + e.getMessage());
                        }
                    });
        };

        heartbeatsThread.scheduleAtFixedRate(heartbeatThread,0,this.heartbeatMs,TimeUnit.MILLISECONDS);
    }


    public void handleIncomingHeartbeat(HeartbeatRequest heartbeatRequest){
        log.trace("Received heartbeat from node " + heartbeatRequest.getSenderId() + " on node " + this.getId());
        updateTerm(heartbeatRequest.getTerm());
        ElectionManager.getInstance().resetElectionTimeout();
    }

    // called by user of the cluster
    public void apply(String command) throws RuntimeException{
        assert (this.serverState.equals(ServerState.LEADER));
        LogManager.getInstance().append(new LogEntry(this.getCurrentTerm(),command.getBytes(StandardCharsets.UTF_8)));

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
            try {
                raftPersistantState.setCurrentTerm(term);
                setVotedFor(null);
            }catch (IOException e){
                return Boolean.FALSE;
            }
            this.changeStateToFollower();
            termUpdated = Boolean.TRUE;
            log.trace("Node " + this.getId()+ " transitioned to follower");
        }
        return termUpdated;
    }
    public Integer getVotedFor(){
        return raftPersistantState.getVotedFor();
    }

    public void setVotedFor(Integer votedFor) throws IOException{
        raftPersistantState.setVotedFor(votedFor);
    }
    public void incrementTerm() throws IOException{
        this.raftPersistantState.setCurrentTerm(this.getCurrentTerm() + 1);
    }





}
