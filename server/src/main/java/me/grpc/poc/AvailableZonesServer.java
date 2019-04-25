package me.grpc.poc;

import static java.lang.String.format;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerInterceptors;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import me.grpc.poc.AvailableZonesGrpc.AvailableZonesImplBase;
import me.grpc.poc.AvailableZonesProto.AvailableZonesInput;
import me.grpc.poc.AvailableZonesProto.AvailableZonesOutput;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

@Slf4j
public class AvailableZonesServer {
    public static final int PORT = 5050;

    private Server server;

    private void start() throws IOException {
        server = ServerBuilder.forPort(PORT)
                .addService(ServerInterceptors.intercept(new AvailableZones(),
                        new TokenAuthInterceptor(),
                        new RequestLoggingInterceptor()))
                .build()
                .start();
        log.info("Server started on port: {}", PORT);
        Runtime.getRuntime().addShutdownHook(new Thread(AvailableZonesServer.this::stop));
    }

    private void stop() {
        if (server != null) {
            log.info("Server stopped!");
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final AvailableZonesServer server = new AvailableZonesServer();
        server.start();
        server.blockUntilShutdown();
    }


    // AvailableZonesImplBase - autogenerated class from .proto file
    static class AvailableZones extends AvailableZonesImplBase {

        private final static Random RND = new Random();

        // methods from .proto file
        @Override
        public void selectZone(AvailableZonesInput request, StreamObserver<AvailableZonesOutput> responseObserver) {
            log.info("Get available zones: {}", request.toString());
            AvailableZonesOutput response = AvailableZonesOutput.newBuilder()
                    .setCompute(format("Compute_%s", UUID.randomUUID().toString()))
                    .setSriov(request.getSriov())
                    .putPhysicalNetworkMap(
                            String.format("CPU_%d_networks_%d_compute_%s", request.getCpus(), 2, UUID.randomUUID().toString()), randomInt(4))
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        private static int randomInt(int max) {
            return RND.nextInt(max);
        }
    }

}
