package com.loplyy.subscriber_service.grpc;

import com.loplyy.subscriber_service.service.SubscriberServiceImpl;
import com.lopply.subscriber.SubscriberServiceGrpc;
import com.lopply.subscriber.Subscriberservice;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SubscriberGrpcService extends SubscriberServiceGrpc.SubscriberServiceImplBase {

    private Logger logger = LoggerFactory.getLogger(SubscriberGrpcService.class);
    private final SubscriberServiceImpl subscriberService;

    public SubscriberGrpcService(SubscriberServiceImpl subscriberService) {
        this.subscriberService = subscriberService;
    }

    @Override
    public void getSubscriberIdByUId(Subscriberservice.SubscriberUIdRequest request, StreamObserver<Subscriberservice.Subscriber> responseObserver) {
        subscriberService.getSubscriberIdByUId(request.getUid())
                .switchIfEmpty(Mono.error(new RuntimeException("Subscriber not found")))
                .map(subscriber -> Subscriberservice.Subscriber.newBuilder()
                        .setId(subscriber.getId())
                        .build()).subscribe(
                        resp -> {
                            responseObserver.onNext(resp);
                            responseObserver.onCompleted();
                        },
                        error -> {
                            logger.warn(error.getMessage());
                            responseObserver.onError(error);
                        }
                );
    }
}
