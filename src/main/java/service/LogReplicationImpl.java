package service;

import io.grpc.stub.StreamObserver;
import org.butterchicken.raft.consensus.RPCAdapter;
import org.butterchicken.raft.consensus.request.AppendEntriesResponse;
import proto.LogReplicationServiceGrpc;
import proto.RPC;

public class LogReplicationImpl extends LogReplicationServiceGrpc.LogReplicationServiceImplBase {
    @Override
    public void appendEntriesRPCHandler(RPC.AppendEntriesRPCRequest appendEntriesRPCRequest , StreamObserver<RPC.AppendEntriesRPCResponse> responseStreamObserver){
        RPC.AppendEntriesRPCResponse appendEntriesRPCResponse = RPCAdapter.appendEntriesHandle(appendEntriesRPCRequest);
        responseStreamObserver.onNext(appendEntriesRPCResponse);
        responseStreamObserver.onCompleted();
    }
}
