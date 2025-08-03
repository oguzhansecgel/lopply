package com.loplyy.subscriber_service.controller;

import com.loplyy.subscriber_service.dto.response.playlist.GetSubscriberPlaylistResponse;
import com.loplyy.subscriber_service.service.SubscriberPlaylistServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/playlists")
public class SubscriberPlaylistController {

    private final SubscriberPlaylistServiceImpl subscriberPlaylistService;

    public SubscriberPlaylistController(SubscriberPlaylistServiceImpl subscriberPlaylistService) {
        this.subscriberPlaylistService = subscriberPlaylistService;
    }

    @GetMapping("/test")
    public Mono<ResponseEntity<Flux<GetSubscriberPlaylistResponse>>> getSubscriberPlaylist() {
        return Mono.just(
                ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(subscriberPlaylistService.getAllSubscriberPlaylists())
        );
    }
}
