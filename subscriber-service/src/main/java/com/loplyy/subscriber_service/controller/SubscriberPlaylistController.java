package com.loplyy.subscriber_service.controller;

import com.loplyy.subscriber_service.config.JwtService;
import com.loplyy.subscriber_service.dto.request.playlist.CreatePlaylistItemRequest;
import com.loplyy.subscriber_service.dto.request.playlist.CreatePlaylistRequest;
import com.loplyy.subscriber_service.dto.response.playlist.GetPlaylistWithItem;
import com.loplyy.subscriber_service.dto.response.playlist.GetSubscriberPlaylistResponse;
import com.loplyy.subscriber_service.service.SubscriberPlaylistServiceImpl;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/playlists")
public class SubscriberPlaylistController {

    private final SubscriberPlaylistServiceImpl subscriberPlaylistService;
    private final JwtService jwtService;
    private final Logger logger = LoggerFactory.getLogger(SubscriberPlaylistController.class);
    public SubscriberPlaylistController(SubscriberPlaylistServiceImpl subscriberPlaylistService, JwtService jwtService) {
        this.subscriberPlaylistService = subscriberPlaylistService;
        this.jwtService = jwtService;
    }

    @GetMapping("/test")
    public Mono<ResponseEntity<Flux<GetSubscriberPlaylistResponse>>> getSubscriberPlaylist() {
        return Mono.just(
                ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(subscriberPlaylistService.getAllSubscriberPlaylists())
        );
    }

    @GetMapping("/playlist-with-item")
    public Mono<ResponseEntity<Mono<GetPlaylistWithItem>>> getPlaylistWithItem(@RequestParam("playlistUId") String playlistUId) {
        return Mono.just(
                ResponseEntity.ok()
                        .body(subscriberPlaylistService.getPlaylistWithItem(playlistUId))
        );
    }

//    @DeleteMapping("/delete-playlist")
//    public Mono<ResponseEntity<Mono<Void>>> deletePlaylist(@RequestParam("playlistUId") String playlistUId, ServerWebExchange exchange) {
//
//    }

    @PostMapping("/create-playlist")
    public Mono<ResponseEntity<Void>> createPlaylist(@RequestBody CreatePlaylistRequest createPlaylistRequest, ServerWebExchange serverWebExchange) {
        //TODO requestten isPublic alanı false geliyor.
        String authHeader = serverWebExchange.getRequest().getHeaders().getFirst("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }

        String token = authHeader.substring(7);
        Claims claims;

        try {
            claims = jwtService.extractAllClaims(token);
        } catch (Exception e) {
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }

        String uuid = claims.get("UUID", String.class);
        return subscriberPlaylistService.createSubscriberPlaylist(createPlaylistRequest, uuid)
                .then(Mono.just(ResponseEntity.ok().build()));
    }

    @PostMapping("/create-playlist-item")
    public Mono<ResponseEntity<Void>> createPlaylistItem(@RequestBody CreatePlaylistItemRequest createPlaylistItemRequest, ServerWebExchange serverWebExchange) {
        return subscriberPlaylistService.addPlaylistItem(createPlaylistItemRequest)
                .then(Mono.just(ResponseEntity.ok().build()));
    }
}
