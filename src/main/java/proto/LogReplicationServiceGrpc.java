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
public final class LogReplicationServiceGrpc {

  private LogReplicationServiceGrpc() {}

  public static final String SERVICE_NAME = "LogReplicationService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<proto.RPC.AppendEntriesRPCRequest,
      proto.RPC.AppendEntriesRPCResponse> getAppendEntriesRPCHandlerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "appendEntriesRPCHandler",
      requestType = proto.RPC.AppendEntriesRPCRequest.class,
      responseType = proto.RPC.AppendEntriesRPCResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<proto.RPC.AppendEntriesRPCRequest,
      proto.RPC.AppendEntriesRPCResponse> getAppendEntriesRPCHandlerMethod() {
    io.grpc.MethodDescriptor<proto.RPC.AppendEntriesRPCRequest, proto.RPC.AppendEntriesRPCResponse> getAppendEntriesRPCHandlerMethod;
    if ((getAppendEntriesRPCHandlerMethod = LogReplicationServiceGrpc.getAppendEntriesRPCHandlerMethod) == null) {
      synchronized (LogReplicationServiceGrpc.class) {
        if ((getAppendEntriesRPCHandlerMethod = LogReplicationServiceGrpc.getAppendEntriesRPCHandlerMethod) == null) {
          LogReplicationServiceGrpc.getAppendEntriesRPCHandlerMethod = getAppendEntriesRPCHandlerMethod = 
              io.grpc.MethodDescriptor.<proto.RPC.AppendEntriesRPCRequest, proto.RPC.AppendEntriesRPCResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "LogReplicationService", "appendEntriesRPCHandler"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.RPC.AppendEntriesRPCRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.RPC.AppendEntriesRPCResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new LogReplicationServiceMethodDescriptorSupplier("appendEntriesRPCHandler"))
                  .build();
          }
        }
     }
     return getAppendEntriesRPCHandlerMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static LogReplicationServiceStub newStub(io.grpc.Channel channel) {
    return new LogReplicationServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static LogReplicationServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new LogReplicationServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static LogReplicationServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new LogReplicationServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class LogReplicationServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void appendEntriesRPCHandler(proto.RPC.AppendEntriesRPCRequest request,
        io.grpc.stub.StreamObserver<proto.RPC.AppendEntriesRPCResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getAppendEntriesRPCHandlerMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getAppendEntriesRPCHandlerMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                proto.RPC.AppendEntriesRPCRequest,
                proto.RPC.AppendEntriesRPCResponse>(
                  this, METHODID_APPEND_ENTRIES_RPCHANDLER)))
          .build();
    }
  }

  /**
   */
  public static final class LogReplicationServiceStub extends io.grpc.stub.AbstractStub<LogReplicationServiceStub> {
    private LogReplicationServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private LogReplicationServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LogReplicationServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new LogReplicationServiceStub(channel, callOptions);
    }

    /**
     */
    public void appendEntriesRPCHandler(proto.RPC.AppendEntriesRPCRequest request,
        io.grpc.stub.StreamObserver<proto.RPC.AppendEntriesRPCResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getAppendEntriesRPCHandlerMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class LogReplicationServiceBlockingStub extends io.grpc.stub.AbstractStub<LogReplicationServiceBlockingStub> {
    private LogReplicationServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private LogReplicationServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LogReplicationServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new LogReplicationServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public proto.RPC.AppendEntriesRPCResponse appendEntriesRPCHandler(proto.RPC.AppendEntriesRPCRequest request) {
      return blockingUnaryCall(
          getChannel(), getAppendEntriesRPCHandlerMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class LogReplicationServiceFutureStub extends io.grpc.stub.AbstractStub<LogReplicationServiceFutureStub> {
    private LogReplicationServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private LogReplicationServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LogReplicationServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new LogReplicationServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<proto.RPC.AppendEntriesRPCResponse> appendEntriesRPCHandler(
        proto.RPC.AppendEntriesRPCRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getAppendEntriesRPCHandlerMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_APPEND_ENTRIES_RPCHANDLER = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final LogReplicationServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(LogReplicationServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_APPEND_ENTRIES_RPCHANDLER:
          serviceImpl.appendEntriesRPCHandler((proto.RPC.AppendEntriesRPCRequest) request,
              (io.grpc.stub.StreamObserver<proto.RPC.AppendEntriesRPCResponse>) responseObserver);
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

  private static abstract class LogReplicationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    LogReplicationServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return proto.RPC.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("LogReplicationService");
    }
  }

  private static final class LogReplicationServiceFileDescriptorSupplier
      extends LogReplicationServiceBaseDescriptorSupplier {
    LogReplicationServiceFileDescriptorSupplier() {}
  }

  private static final class LogReplicationServiceMethodDescriptorSupplier
      extends LogReplicationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    LogReplicationServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (LogReplicationServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new LogReplicationServiceFileDescriptorSupplier())
              .addMethod(getAppendEntriesRPCHandlerMethod())
              .build();
        }
      }
    }
    return result;
  }
}
