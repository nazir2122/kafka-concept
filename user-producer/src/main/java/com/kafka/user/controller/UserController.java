package com.kafka.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kafka.user.config.KafkaProperties;
import com.kafka.user.model.UserInfo;
import com.kafka.user.model.UserResponse;
import com.kafka.user.service.UserHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/v1")
@Slf4j
//junit test cases for below class
public class UserController {

    @Autowired
    UserHandlerService userHandlerService;

    @GetMapping(value = "/")
    ResponseEntity<String> userService() {
        return ResponseEntity.ok("user-producer service");
    }

    @GetMapping("/health")
    ResponseEntity<String> health() {
        return new ResponseEntity<>("user producer service up and running...", HttpStatus.OK);
    }

    @PostMapping("/process")
    public ResponseEntity<UserResponse> processData(@RequestBody UserInfo userInfo) throws JsonProcessingException {
        log.info("------------userInfo : {}", userInfo);
        UserResponse userResponse = new UserResponse();
        userResponse = userHandlerService.userResponse(userInfo, userResponse);
        log.info("---------------userResponse : " + userResponse);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
}
