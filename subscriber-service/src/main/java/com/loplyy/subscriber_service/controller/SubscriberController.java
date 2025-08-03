package com.loplyy.subscriber_service.controller;

import com.loplyy.subscriber_service.config.JwtService;
import com.loplyy.subscriber_service.dto.request.subscriber.CreateSubscriberProfileRequest;
import com.loplyy.subscriber_service.dto.request.subscriber.UpdateSubscriberProfileRequest;
import com.loplyy.subscriber_service.dto.response.subscriber.GetSubscriberIdByUId;
import com.loplyy.subscriber_service.service.SubscriberServiceImpl;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/subscribers")
public class SubscriberController {

    private final SubscriberServiceImpl subscriberService;
    private final JwtService jwtService;
    private Logger logger = LoggerFactory.getLogger(SubscriberController.class);

    public SubscriberController(JwtService jwtService, SubscriberServiceImpl subscriberService) {
        this.jwtService = jwtService;
        this.subscriberService = subscriberService;
    }

    @GetMapping("/get-subscriber-id-by-uid")
    public Mono<ResponseEntity<GetSubscriberIdByUId>> getSubscriberIdByUId(
            @RequestParam("uid") String uid, ServerWebExchange exchange) {

        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }

        return subscriberService.getSubscriberIdByUId(uid)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> {
                    logger.error("/api/subscribers/get-subscriber-id-by-uid {}", e.getMessage());
                    return Mono.just(ResponseEntity.badRequest().build());
                });
    }

    @PostMapping("/create-subscriber-profile")
    public Mono<ResponseEntity<Void>> createSubscriberProfile(@RequestBody CreateSubscriberProfileRequest request, ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }

        String token = authHeader.substring(7);
        Claims claims;

        try {
            claims = jwtService.extractAllClaims(token);
        } catch (Exception e) {
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null));
        }

        String uuid = claims.get("UUID",String.class);
        request.setAccountUId(UUID.fromString(uuid));
        return subscriberService.createSubscriberProfile(request).map(ResponseEntity::ok);
    }

    @PostMapping("/update-subscriber-profile")
    public Mono<ResponseEntity<Void>> updateSubscriberProfile(@RequestBody UpdateSubscriberProfileRequest request, ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }
        return subscriberService.updateSubscriberProfile(request).map(ResponseEntity::ok);
    }
}
