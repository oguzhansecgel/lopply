package com.loplyy.subscriber_service.service;

import com.loplyy.subscriber_service.client.Auth.AuthServiceClient;
import com.loplyy.subscriber_service.dto.request.subscriber.CreateSubscriberProfileRequest;
import com.loplyy.subscriber_service.dto.request.subscriber.UpdateSubscriberProfileRequest;
import com.loplyy.subscriber_service.dto.response.subscriber.GetSubscriberIdByUId;
import com.loplyy.subscriber_service.entity.Subscriber;
import com.loplyy.subscriber_service.repository.SubscriberRepository;
import com.lopply.subscriber.SubscriberServiceGrpc;
import com.lopply.subscriber.Subscriberservice;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class SubscriberServiceImpl extends SubscriberServiceGrpc.SubscriberServiceImplBase {

    private final SubscriberRepository subscriberRepository;
    private final AuthServiceClient authServiceClient;

    public SubscriberServiceImpl(SubscriberRepository subscriberRepository, AuthServiceClient authServiceClient) {
        this.subscriberRepository = subscriberRepository;
        this.authServiceClient = authServiceClient;
    }

    public Mono<Void> updateSubscriberProfile(UpdateSubscriberProfileRequest updateSubscriberProfileRequest) {
        return subscriberRepository.getSubscriberIdByUuid(updateSubscriberProfileRequest.getUid().toString())
                .switchIfEmpty(Mono.error(new RuntimeException("Subscriber Not Found")))
                .flatMap(subscriber -> {
                    subscriber.setFirstName(updateSubscriberProfileRequest.getFirstName());
                    subscriber.setLastName(updateSubscriberProfileRequest.getLastName());

                   return subscriberRepository.save(subscriber).then();
                });
    }

    public Mono<Void> createSubscriberProfile(CreateSubscriberProfileRequest request) {
        return authServiceClient.getUserIdByUid(request.getAccountUId())
                .switchIfEmpty(Mono.error(new RuntimeException("User not found in Auth Service")))
                .flatMap(userIdResponse -> {
                    Subscriber subscriber = new Subscriber();
                    subscriber.setUuid(UUID.randomUUID());
                    subscriber.setFirstName(request.getFirstName());
                    subscriber.setLastName(request.getLastName());
                    subscriber.setAccountId(userIdResponse.getId());

                    return subscriberRepository.save(subscriber).then();
                });
    }

    @Override
    public void getSubscriberIdByUId(Subscriberservice.SubscriberUIdRequest request, StreamObserver<Subscriberservice.Subscriber> responseObserver) {
        subscriberRepository.getSubscriberIdByUuid(request.getUid())
                .switchIfEmpty(Mono.error(new RuntimeException("Subscriber not found")))
                .map(subscriber -> Subscriberservice.Subscriber.newBuilder()
                        .setId(subscriber.getId())
                        .build()).subscribe(
                        resp -> {
                            responseObserver.onNext(resp);
                            responseObserver.onCompleted();
                        },
                        error -> {
                            responseObserver.onError(error);
                        }
                );
    }
}
