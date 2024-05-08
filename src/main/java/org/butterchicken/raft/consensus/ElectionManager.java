package org.butterchicken.raft.consensus;

import lombok.extern.log4j.Log4j2;
import org.butterchicken.raft.cluster.ClusterNode;
import org.butterchicken.raft.consensus.request.VoteRequest;
import org.butterchicken.raft.consensus.response.VoteResponse;


import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantLock;

@Log4j2
public class ElectionManager {

    Integer electionTimeoutMs;
    private final Timer electionThread;
    private TimerTask currentElection;
    ReentrantLock lock =  new ReentrantLock();
    RaftServer server;

    static ElectionManager instance;

    private ElectionManager(){
        server = RaftServer.getInstance();
        electionThread = new Timer();
        currentElection = new Election();
        resetElectionTimeout();
    }


    public VoteResponse requestVoteHandle(VoteRequest voteRequest){
        lock.lock();
        server.updateTerm(voteRequest.getTerm());
        log.trace("Received vote request from node  " + voteRequest.getCandidateId() + " on node " + server.getId());
        if(voteRequest.getTerm() < server.getCurrentTerm()){
            log.trace("Not granting vote to " + voteRequest.getCandidateId());
            return new VoteResponse(-1,false);
        }

        log.trace("Vote granted to server " + voteRequest.getCandidateId());
        server.setVotedFor(voteRequest.getCandidateId());
        return new VoteResponse(server.getCurrentTerm(),Boolean.TRUE);
    }




    private Boolean requestVote(){
        Integer CONSENSUS_THRESHOLD = server.getCluster().size()/2 + 1;
        Integer votesReceived = 0;
        for(ClusterNode clusterNode : server.getCluster()){
            if(!clusterNode.getId().equals(server.getId())){

                try {
                    log.debug("Requesting vote from node " + clusterNode.getId() + " by node " + server.getId() + " for term " + server.getCurrentTerm());
                    VoteResponse voteResponse = RPCAdapter.requestVote(clusterNode, new VoteRequest(server.getCurrentTerm() + 1, server.getId()));
                    votesReceived += voteResponse.getVoteGranted().equals(Boolean.TRUE) ? 1 : 0;
                }catch (Exception e){
                    log.error("Failed to send vote request to " + clusterNode.getHost() + ":" + clusterNode.getPort());
                }
            }else{
                server.setVotedFor(server.getId());
                votesReceived++;
            }
        }

        if(votesReceived >= CONSENSUS_THRESHOLD){
            log.trace("New leader has been elected " + server.getId());
            server.incrementTerm();
            server.changeStateToLeader();
            return Boolean.TRUE;
        }else{
            log.debug("Consensus not formed");
            return Boolean.FALSE;
        }
    }

    private class Election extends TimerTask {
        @Override
        public void run(){
            log.trace("Election has been started  on node " + server.getId());
            server.changeStateToCandidate();
            Boolean consensusFormed = requestVote();
            if(!consensusFormed){
                resetElectionTimeout();
            }
        }
    }


     void resetElectionTimeout(){
        Random random = new Random();
        lock.lock();
        currentElection.cancel();
        this.electionTimeoutMs = server.heartbeatMs + 2000 + random.nextInt(3000);
        currentElection = new Election();
        electionThread.schedule(currentElection,this.electionTimeoutMs);
        lock.unlock();
    }



    static public ElectionManager getInstance(){
        if(instance == null){
            instance = new ElectionManager();
        }
        return instance;
    }




}
