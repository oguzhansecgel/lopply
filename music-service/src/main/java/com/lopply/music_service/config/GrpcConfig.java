package com.lopply.music_service.config;

import com.lopply.music_service.service.MusicServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.protobuf.services.ProtoReflectionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class GrpcConfig {

    @Value("${grpc.port}")
    private int port;

    @Bean
    public Server grpcServer(MusicServiceImpl musicService) throws IOException {
        Server server = ServerBuilder.forPort(port)
                .addService(musicService)
                .addService(ProtoReflectionService.newInstance())
                .build()
                .start();


        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown));

        return server;
    }
}