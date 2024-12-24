package com.kafka.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "user/v1")
public class ConsumerController {
    @GetMapping(value = "/")
    ResponseEntity<String> userService() {
        return ResponseEntity.ok("user-consumer service");
    }

    @GetMapping("/health")
    ResponseEntity<String> health() {
        return new ResponseEntity<>("user consumer service up and running...", HttpStatus.OK);
    }
}
