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
public final class RequestVoteServiceGrpc {

  private RequestVoteServiceGrpc() {}

  public static final String SERVICE_NAME = "RequestVoteService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<proto.RPC.VoteRPCRequest,
      proto.RPC.VoteRPCResponse> getRequestVoteRPCHandlerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "requestVoteRPCHandler",
      requestType = proto.RPC.VoteRPCRequest.class,
      responseType = proto.RPC.VoteRPCResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<proto.RPC.VoteRPCRequest,
      proto.RPC.VoteRPCResponse> getRequestVoteRPCHandlerMethod() {
    io.grpc.MethodDescriptor<proto.RPC.VoteRPCRequest, proto.RPC.VoteRPCResponse> getRequestVoteRPCHandlerMethod;
    if ((getRequestVoteRPCHandlerMethod = RequestVoteServiceGrpc.getRequestVoteRPCHandlerMethod) == null) {
      synchronized (RequestVoteServiceGrpc.class) {
        if ((getRequestVoteRPCHandlerMethod = RequestVoteServiceGrpc.getRequestVoteRPCHandlerMethod) == null) {
          RequestVoteServiceGrpc.getRequestVoteRPCHandlerMethod = getRequestVoteRPCHandlerMethod = 
              io.grpc.MethodDescriptor.<proto.RPC.VoteRPCRequest, proto.RPC.VoteRPCResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "RequestVoteService", "requestVoteRPCHandler"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.RPC.VoteRPCRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.RPC.VoteRPCResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new RequestVoteServiceMethodDescriptorSupplier("requestVoteRPCHandler"))
                  .build();
          }
        }
     }
     return getRequestVoteRPCHandlerMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static RequestVoteServiceStub newStub(io.grpc.Channel channel) {
    return new RequestVoteServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static RequestVoteServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new RequestVoteServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static RequestVoteServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new RequestVoteServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class RequestVoteServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void requestVoteRPCHandler(proto.RPC.VoteRPCRequest request,
        io.grpc.stub.StreamObserver<proto.RPC.VoteRPCResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRequestVoteRPCHandlerMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getRequestVoteRPCHandlerMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                proto.RPC.VoteRPCRequest,
                proto.RPC.VoteRPCResponse>(
                  this, METHODID_REQUEST_VOTE_RPCHANDLER)))
          .build();
    }
  }

  /**
   */
  public static final class RequestVoteServiceStub extends io.grpc.stub.AbstractStub<RequestVoteServiceStub> {
    private RequestVoteServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private RequestVoteServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RequestVoteServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new RequestVoteServiceStub(channel, callOptions);
    }

    /**
     */
    public void requestVoteRPCHandler(proto.RPC.VoteRPCRequest request,
        io.grpc.stub.StreamObserver<proto.RPC.VoteRPCResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRequestVoteRPCHandlerMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class RequestVoteServiceBlockingStub extends io.grpc.stub.AbstractStub<RequestVoteServiceBlockingStub> {
    private RequestVoteServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private RequestVoteServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RequestVoteServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new RequestVoteServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public proto.RPC.VoteRPCResponse requestVoteRPCHandler(proto.RPC.VoteRPCRequest request) {
      return blockingUnaryCall(
          getChannel(), getRequestVoteRPCHandlerMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class RequestVoteServiceFutureStub extends io.grpc.stub.AbstractStub<RequestVoteServiceFutureStub> {
    private RequestVoteServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private RequestVoteServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RequestVoteServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new RequestVoteServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<proto.RPC.VoteRPCResponse> requestVoteRPCHandler(
        proto.RPC.VoteRPCRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRequestVoteRPCHandlerMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REQUEST_VOTE_RPCHANDLER = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final RequestVoteServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(RequestVoteServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_REQUEST_VOTE_RPCHANDLER:
          serviceImpl.requestVoteRPCHandler((proto.RPC.VoteRPCRequest) request,
              (io.grpc.stub.StreamObserver<proto.RPC.VoteRPCResponse>) responseObserver);
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

  private static abstract class RequestVoteServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    RequestVoteServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return proto.RPC.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("RequestVoteService");
    }
  }

  private static final class RequestVoteServiceFileDescriptorSupplier
      extends RequestVoteServiceBaseDescriptorSupplier {
    RequestVoteServiceFileDescriptorSupplier() {}
  }

  private static final class RequestVoteServiceMethodDescriptorSupplier
      extends RequestVoteServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    RequestVoteServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (RequestVoteServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new RequestVoteServiceFileDescriptorSupplier())
              .addMethod(getRequestVoteRPCHandlerMethod())
              .build();
        }
      }
    }
    return result;
  }
}
