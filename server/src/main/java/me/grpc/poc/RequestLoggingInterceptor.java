package me.grpc.poc;

import io.grpc.ForwardingServerCallListener.SimpleForwardingServerCallListener;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCall.Listener;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestLoggingInterceptor implements ServerInterceptor {

    @Override
    public <ReqT, RespT> Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata,
            ServerCallHandler<ReqT, RespT> next) {
        ServerCall<ReqT, RespT> customCall = new LoggableServerCall<>(serverCall);
        Listener<ReqT> originalListener = next.startCall(customCall, metadata);

        return new SimpleForwardingServerCallListener<ReqT>(originalListener) {
            @Override
            public void onMessage(ReqT message) {
                log.info("Incoming request: {} {}", metadata, message);
                super.onMessage(message);
            }
        };
    }
}
