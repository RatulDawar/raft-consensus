package service;

import io.grpc.stub.StreamObserver;
import org.butterchicken.raft.consensus.RPCAdapter;
import org.butterchicken.raft.consensus.RaftServer;
import org.butterchicken.raft.consensus.response.VoteResponse;
import proto.HeatbeatServiceGrpc;
import proto.RPC;

public class HeartbeatImpl extends HeatbeatServiceGrpc.HeatbeatServiceImplBase {
    @Override
    public void sendHeartbeatRPC(RPC.HeartbeatRPCRequest request, StreamObserver<RPC.VoidRPCResponse> responseStreamObserver){
        RPCAdapter.heartbeatHandler(request);
        RPC.VoidRPCResponse voidRPCResponse = RPC.VoidRPCResponse.newBuilder().build();
        responseStreamObserver.onNext(voidRPCResponse);
        responseStreamObserver.onCompleted();
    }

}
