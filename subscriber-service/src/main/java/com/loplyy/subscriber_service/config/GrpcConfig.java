package com.loplyy.subscriber_service.config;

import com.loplyy.subscriber_service.grpc.SubscriberGrpcService;
import com.loplyy.subscriber_service.service.SubscriberServiceImpl;
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
    public Server grpcServer(SubscriberGrpcService subscriberService) throws IOException {
        Server server = ServerBuilder.forPort(port)
                .addService(subscriberService)
                .addService(ProtoReflectionService.newInstance())
                .build()
                .start();


        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown));

        return server;
    }
}