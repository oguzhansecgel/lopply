package com.loplyy.subscriber_service.client.Music;

import com.loplyy.subscriber_service.client.Auth.GetSubscriberIdByUid;
import com.loplyy.subscriber_service.client.Music.dto.GetByUIdMusic;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class MusicServiceClient {
    private final WebClient.Builder webClientBuilder;

    public MusicServiceClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<GetByUIdMusic> getByUIdMusic(String uid) {
        return webClientBuilder.build()
                .get()
                .uri("http://localhost:8084/api/music/get-by-uid?uid={uid}", uid)
                .retrieve()
//                .onStatus(HttpStatus::isError, clientResponse ->
//                        clientResponse.bodyToMono(String.class)
//                                .flatMap(errorBody -> Mono.error(new RuntimeException("Hata: " + errorBody)))
//                )
                .bodyToMono(GetByUIdMusic.class);
    }
}
