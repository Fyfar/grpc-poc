package me.grpc.poc;

import io.grpc.CallCredentials;
import io.grpc.Metadata;
import io.grpc.Metadata.Key;
import io.grpc.Status;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executor;

@Slf4j
public class TokenAuthCredentials extends CallCredentials {

    private static final Key<String> METADATA_AUTH_KEY = Metadata.Key.of("Authentication", Metadata.ASCII_STRING_MARSHALLER);

    @Override
    public void applyRequestMetadata(RequestInfo requestInfo, Executor executor, MetadataApplier metadataApplier) {
        executor.execute(() -> {
            try {
                Metadata metadata = new Metadata();
                metadata.put(METADATA_AUTH_KEY, UserServiceStub.getUserToken());
                metadataApplier.apply(metadata);
            } catch (Exception e) {
                metadataApplier.fail(Status.UNAUTHENTICATED.withCause(e));
            }
        });
    }

    @Override
    public void thisUsesUnstableApi() {}
}
