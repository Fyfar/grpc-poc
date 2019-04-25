package me.grpc.poc;

import io.grpc.Context;
import io.grpc.Contexts;
import io.grpc.Metadata;
import io.grpc.Metadata.Key;
import io.grpc.ServerCall;
import io.grpc.ServerCall.Listener;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TokenAuthInterceptor implements ServerInterceptor {

    private static final Listener NOOP_LISTENER = new Listener() {};
    private static final Key<String> METADATA_AUTH_KEY = Metadata.Key.of("Authentication", Metadata.ASCII_STRING_MARSHALLER);

    @Override
    public <ReqT, RespT> Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata,
            ServerCallHandler<ReqT, RespT> serverCallHandler) {
        String token = metadata.get(METADATA_AUTH_KEY);

        if (token == null) {
            log.info("User isn't authenticated");
            serverCall.close(Status.UNAUTHENTICATED.withDescription("Token is missing"), metadata);
            return NOOP_LISTENER;
        }

        log.info("User token is: {}", token);
        return Contexts.interceptCall(Context.current(), serverCall, metadata, serverCallHandler);
    }
}
