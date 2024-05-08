package org.butterchicken.raft.consensus;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.butterchicken.raft.cluster.ClusterNode;
import org.butterchicken.raft.consensus.request.HeartbeatRequest;
import org.butterchicken.raft.consensus.request.VoteRequest;
import org.butterchicken.raft.consensus.response.VoteResponse;
import proto.HeatbeatServiceGrpc;
import proto.RPC;
import proto.RequestVoteServiceGrpc;

import java.io.IOException;

public class RPCAdapter {
    static public VoteResponse requestVote(ClusterNode clusterNode, VoteRequest voteRequest) throws Exception {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(clusterNode.getHost(), clusterNode.getPort()).usePlaintext().build();
        RequestVoteServiceGrpc.RequestVoteServiceBlockingStub bookStub = RequestVoteServiceGrpc.newBlockingStub(channel);
        RPC.VoteRPCResponse response = bookStub.requestVoteRPCHandler(RPC.VoteRPCRequest.newBuilder().setCandidateId(voteRequest.getCandidateId()).setTerm(voteRequest.getTerm()).build());
        return new VoteResponse(response.getTerm(), response.getVoteGranted());
    }

    static public VoteResponse requestVoteHandle(RPC.VoteRPCRequest voteRPCRequest){
        return ElectionManager.getInstance().requestVoteHandle(new VoteRequest(voteRPCRequest.getTerm(),voteRPCRequest.getCandidateId()));
    }

    static public void sendHeartbeat(ClusterNode clusterNode,HeartbeatRequest heartbeatRequest) throws IOException{
        ManagedChannel channel = ManagedChannelBuilder.forAddress(clusterNode.getHost(),clusterNode.getPort()).usePlaintext().build();
        HeatbeatServiceGrpc.HeatbeatServiceBlockingStub bookStub = HeatbeatServiceGrpc.newBlockingStub(channel);
        RPC.VoidRPCResponse response = bookStub.sendHeartbeatRPC(RPC.HeartbeatRPCRequest.newBuilder().setSenderId(heartbeatRequest.getSenderId()).setTerm(heartbeatRequest.getTerm()).build());
    }

    static public void heartbeatHandler(RPC.HeartbeatRPCRequest heartbeatRPCRequest){
        RaftServer.getInstance().handleIncomingHeartbeat(new HeartbeatRequest(heartbeatRPCRequest.getSenderId(), heartbeatRPCRequest.getTerm()));
    }

}
