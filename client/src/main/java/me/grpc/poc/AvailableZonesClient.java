package me.grpc.poc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import me.grpc.poc.AvailableZonesGrpc.AvailableZonesBlockingStub;
import me.grpc.poc.AvailableZonesProto.AvailableZonesInput;
import me.grpc.poc.AvailableZonesProto.AvailableZonesOutput;

import java.util.concurrent.TimeUnit;

@Slf4j
public class AvailableZonesClient {
    private final ManagedChannel channel;
    private final AvailableZonesBlockingStub blockingStub;

    public AvailableZonesClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext().build());
    }

    AvailableZonesClient(ManagedChannel channel) {
        this.channel = channel;
        blockingStub = AvailableZonesGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public AvailableZonesOutput getAvailableZones(AvailableZonesInput input) {
        log.info("Send payload: {}", input.toString());

        try {
            return blockingStub.selectZone(input);
        } catch (StatusRuntimeException e) {
            log.error("Something went wrong :(", e);
            throw new RuntimeException("Can't get available zones", e);
        }
    }
}
