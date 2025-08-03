package com.loplyy.subscriber_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.FeignClient;

@SpringBootApplication
public class SubscriberServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SubscriberServiceApplication.class, args);
	}

}
