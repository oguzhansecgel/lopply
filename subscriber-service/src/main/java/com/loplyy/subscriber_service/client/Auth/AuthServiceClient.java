package com.loplyy.subscriber_service.client.Auth;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class AuthServiceClient {

    private final WebClient.Builder webClientBuilder;

    public AuthServiceClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<GetSubscriberIdByUid> getUserIdByUid(UUID uid) {
        return webClientBuilder.build()
                .get()
                .uri("http://localhost:8082/api/users/get-user-id-by-uid?uid={uid}", uid)
                .retrieve()
//                .onStatus(HttpStatus::isError, clientResponse ->
//                        clientResponse.bodyToMono(String.class)
//                                .flatMap(errorBody -> Mono.error(new RuntimeException("Hata: " + errorBody)))
//                )
                .bodyToMono(GetSubscriberIdByUid.class);
    }
}
