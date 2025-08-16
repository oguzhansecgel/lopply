package com.loplyy.subscriber_service.client.Music;

import com.lopply.music.MusicServiceGrpc;
import com.lopply.music.Musicservice;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class MusicServiceClient {

    private final MusicServiceGrpc.MusicServiceBlockingStub musicBlockingStub;

    public MusicServiceClient(
            @Value("${grpc.music.host}") String host,
            @Value("${grpc.music.port}") int port) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();

        this.musicBlockingStub = MusicServiceGrpc.newBlockingStub(channel);
    }

    public Musicservice.MusicById getMusicById(String uid) {
        return musicBlockingStub.getMusicByUId(Musicservice.MusicUIdRequest.newBuilder().setUid(uid).build());
    }
}
