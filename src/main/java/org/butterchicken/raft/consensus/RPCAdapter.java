package org.butterchicken.raft.consensus;

import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.butterchicken.raft.cluster.ClusterNode;
import org.butterchicken.raft.consensus.request.AppendEntriesRequest;
import org.butterchicken.raft.consensus.request.AppendEntriesResponse;
import org.butterchicken.raft.consensus.request.HeartbeatRequest;
import org.butterchicken.raft.consensus.request.VoteRequest;
import org.butterchicken.raft.consensus.response.VoteResponse;
import proto.HeatbeatServiceGrpc;
import proto.LogReplicationServiceGrpc;
import proto.RPC;
import proto.RequestVoteServiceGrpc;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class RPCAdapter {
    static public VoteResponse requestVote(ClusterNode clusterNode, VoteRequest voteRequest) throws Exception {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(clusterNode.getHost(), clusterNode.getPort()).usePlaintext().build();
        RequestVoteServiceGrpc.RequestVoteServiceBlockingStub bookStub = RequestVoteServiceGrpc.newBlockingStub(channel);
        RPC.VoteRPCResponse response = bookStub.requestVoteRPCHandler(RPC.VoteRPCRequest.newBuilder().setCandidateId(voteRequest.getCandidateId())
                .setTerm(voteRequest.getTerm())
                .build()
        );
        return new VoteResponse(response.getTerm(), response.getVoteGranted());
    }

    static public VoteResponse requestVoteHandle(RPC.VoteRPCRequest voteRPCRequest){
        return ElectionManager.getInstance().requestVoteHandle(new VoteRequest(voteRPCRequest.getTerm(),voteRPCRequest.getCandidateId()));
    }

    static public void sendHeartbeat(ClusterNode clusterNode,HeartbeatRequest heartbeatRequest) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(clusterNode.getHost(),clusterNode.getPort()).usePlaintext().build();
        HeatbeatServiceGrpc.HeatbeatServiceBlockingStub bookStub = HeatbeatServiceGrpc.newBlockingStub(channel);
        RPC.VoidRPCResponse response = bookStub.sendHeartbeatRPC(RPC.HeartbeatRPCRequest.newBuilder().setSenderId(heartbeatRequest.getSenderId())
                .setTerm(heartbeatRequest.getTerm())
                .build()
        );
    }

    static public void heartbeatHandler(RPC.HeartbeatRPCRequest heartbeatRPCRequest){
        RaftServer.getInstance().handleIncomingHeartbeat(new HeartbeatRequest(heartbeatRPCRequest.getSenderId(), heartbeatRPCRequest.getTerm()));
    }

    static public RPC.AppendEntriesRPCResponse appendEntriesHandle(RPC.AppendEntriesRPCRequest appendEntriesRPCRequest)  {
        List<LogEntry> appendRequestLogEntry = appendEntriesRPCRequest.getLogEntriesList().stream()
                .map(logEntry -> new  LogEntry(logEntry.getTerm(),logEntry.getCommand().toByteArray())).collect(Collectors.toList());
        AppendEntriesResponse appendEntriesResponse = LogManager.getInstance().appendEntriesHandle(
                AppendEntriesRequest.builder()
                        .logEntries(appendRequestLogEntry)
                        .term(appendEntriesRPCRequest.getTerm())
                        .prevLogTerm(appendEntriesRPCRequest.getPrevLogTerm())
                        .prevLogIndex(appendEntriesRPCRequest.getPrevLogIndex())
                        .leaderCommitIndex(appendEntriesRPCRequest.getLeaderCommitIndex())
                        .leaderID(appendEntriesRPCRequest.getLeaderID())
                        .build()
        );
        return RPC.AppendEntriesRPCResponse.newBuilder().setIsReplicated(appendEntriesResponse.getIsReplicated()).build();
    }

    static public AppendEntriesResponse sendAppendEntriesRequest(ClusterNode clusterNode,AppendEntriesRequest appendEntriesRequest){
        ManagedChannel channel = ManagedChannelBuilder.forAddress(clusterNode.getHost(), clusterNode.getPort()).usePlaintext().build();
        LogReplicationServiceGrpc.LogReplicationServiceBlockingStub bookStub = LogReplicationServiceGrpc.newBlockingStub(channel);

        List<RPC.LogEntry> rpcLogEntry = appendEntriesRequest.getLogEntries().stream()
                .map(logEntry -> RPC.LogEntry.newBuilder()
                        .setTerm(logEntry.getTerm())
                        .setCommand(ByteString.copyFrom(logEntry.getCommand()))
                        .build())
                .toList();

        RPC.AppendEntriesRPCResponse response = bookStub.appendEntriesRPCHandler(RPC.AppendEntriesRPCRequest.newBuilder()
                .setLeaderID(appendEntriesRequest.getLeaderID())
                .setPrevLogIndex(appendEntriesRequest.getPrevLogIndex())
                .setPrevLogTerm(appendEntriesRequest.getPrevLogTerm())
                .setLeaderCommitIndex(appendEntriesRequest.getLeaderCommitIndex())
                .addAllLogEntries(rpcLogEntry)
                .setTerm(appendEntriesRequest.getTerm())
                .build()
        );
        return AppendEntriesResponse.builder().isReplicated(response.getIsReplicated()).build();

    }

}
