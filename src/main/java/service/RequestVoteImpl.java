package service;

import io.grpc.stub.StreamObserver;


import lombok.extern.log4j.Log4j2;
import org.butterchicken.raft.consensus.ElectionManager;
import org.butterchicken.raft.consensus.RPCAdapter;
import org.butterchicken.raft.consensus.request.VoteRequest;
import org.butterchicken.raft.consensus.response.VoteResponse;
import proto.RPC;
import proto.RequestVoteServiceGrpc;

@Log4j2
public class RequestVoteImpl extends RequestVoteServiceGrpc.RequestVoteServiceImplBase{

    @Override
    public void requestVoteRPCHandler(RPC.VoteRPCRequest request, StreamObserver<RPC.VoteRPCResponse> responseStreamObserver){
        VoteResponse response = RPCAdapter.requestVoteHandle(request);
        RPC.VoteRPCResponse responseRPC = RPC.VoteRPCResponse.newBuilder().setVoteGranted(response.getVoteGranted()).setTerm(response.getTerm()).build();
        responseStreamObserver.onNext(responseRPC);
        responseStreamObserver.onCompleted();
    }
}
