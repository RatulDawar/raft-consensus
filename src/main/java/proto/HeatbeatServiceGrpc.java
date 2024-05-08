package proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: RPC.proto")
public final class HeatbeatServiceGrpc {

  private HeatbeatServiceGrpc() {}

  public static final String SERVICE_NAME = "HeatbeatService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<proto.RPC.HeartbeatRPCRequest,
      proto.RPC.VoidRPCResponse> getSendHeartbeatRPCMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "sendHeartbeatRPC",
      requestType = proto.RPC.HeartbeatRPCRequest.class,
      responseType = proto.RPC.VoidRPCResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<proto.RPC.HeartbeatRPCRequest,
      proto.RPC.VoidRPCResponse> getSendHeartbeatRPCMethod() {
    io.grpc.MethodDescriptor<proto.RPC.HeartbeatRPCRequest, proto.RPC.VoidRPCResponse> getSendHeartbeatRPCMethod;
    if ((getSendHeartbeatRPCMethod = HeatbeatServiceGrpc.getSendHeartbeatRPCMethod) == null) {
      synchronized (HeatbeatServiceGrpc.class) {
        if ((getSendHeartbeatRPCMethod = HeatbeatServiceGrpc.getSendHeartbeatRPCMethod) == null) {
          HeatbeatServiceGrpc.getSendHeartbeatRPCMethod = getSendHeartbeatRPCMethod = 
              io.grpc.MethodDescriptor.<proto.RPC.HeartbeatRPCRequest, proto.RPC.VoidRPCResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "HeatbeatService", "sendHeartbeatRPC"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.RPC.HeartbeatRPCRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.RPC.VoidRPCResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new HeatbeatServiceMethodDescriptorSupplier("sendHeartbeatRPC"))
                  .build();
          }
        }
     }
     return getSendHeartbeatRPCMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static HeatbeatServiceStub newStub(io.grpc.Channel channel) {
    return new HeatbeatServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static HeatbeatServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new HeatbeatServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static HeatbeatServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new HeatbeatServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class HeatbeatServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void sendHeartbeatRPC(proto.RPC.HeartbeatRPCRequest request,
        io.grpc.stub.StreamObserver<proto.RPC.VoidRPCResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getSendHeartbeatRPCMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSendHeartbeatRPCMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                proto.RPC.HeartbeatRPCRequest,
                proto.RPC.VoidRPCResponse>(
                  this, METHODID_SEND_HEARTBEAT_RPC)))
          .build();
    }
  }

  /**
   */
  public static final class HeatbeatServiceStub extends io.grpc.stub.AbstractStub<HeatbeatServiceStub> {
    private HeatbeatServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private HeatbeatServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HeatbeatServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new HeatbeatServiceStub(channel, callOptions);
    }

    /**
     */
    public void sendHeartbeatRPC(proto.RPC.HeartbeatRPCRequest request,
        io.grpc.stub.StreamObserver<proto.RPC.VoidRPCResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSendHeartbeatRPCMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class HeatbeatServiceBlockingStub extends io.grpc.stub.AbstractStub<HeatbeatServiceBlockingStub> {
    private HeatbeatServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private HeatbeatServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HeatbeatServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new HeatbeatServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public proto.RPC.VoidRPCResponse sendHeartbeatRPC(proto.RPC.HeartbeatRPCRequest request) {
      return blockingUnaryCall(
          getChannel(), getSendHeartbeatRPCMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class HeatbeatServiceFutureStub extends io.grpc.stub.AbstractStub<HeatbeatServiceFutureStub> {
    private HeatbeatServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private HeatbeatServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HeatbeatServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new HeatbeatServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<proto.RPC.VoidRPCResponse> sendHeartbeatRPC(
        proto.RPC.HeartbeatRPCRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSendHeartbeatRPCMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SEND_HEARTBEAT_RPC = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final HeatbeatServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(HeatbeatServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SEND_HEARTBEAT_RPC:
          serviceImpl.sendHeartbeatRPC((proto.RPC.HeartbeatRPCRequest) request,
              (io.grpc.stub.StreamObserver<proto.RPC.VoidRPCResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class HeatbeatServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    HeatbeatServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return proto.RPC.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("HeatbeatService");
    }
  }

  private static final class HeatbeatServiceFileDescriptorSupplier
      extends HeatbeatServiceBaseDescriptorSupplier {
    HeatbeatServiceFileDescriptorSupplier() {}
  }

  private static final class HeatbeatServiceMethodDescriptorSupplier
      extends HeatbeatServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    HeatbeatServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (HeatbeatServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new HeatbeatServiceFileDescriptorSupplier())
              .addMethod(getSendHeartbeatRPCMethod())
              .build();
        }
      }
    }
    return result;
  }
}
