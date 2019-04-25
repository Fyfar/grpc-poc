package me.grpc.poc;

import io.grpc.ForwardingServerCall.SimpleForwardingServerCall;
import io.grpc.ServerCall;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggableServerCall<Req, Resp> extends SimpleForwardingServerCall<Req, Resp> {

    protected LoggableServerCall(ServerCall<Req, Resp> delegate) {
        super(delegate);
    }

    @Override
    public void sendMessage(Resp message) {
        log.info("Response from server call: {}", message);
        super.sendMessage(message);
    }
}
