package com.kafka.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kafka.user.config.KafkaProperties;
import com.kafka.user.model.DecisionResponse;
import com.kafka.user.model.UserInfo;
import com.kafka.user.model.UserResponse;
import com.kafka.user.service.KafkaPublisher;
import com.kafka.user.service.UserHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/v1")
@Slf4j
public class UserController {

    @Autowired
    UserHandlerService userHandlerService;
    @Autowired
    KafkaPublisher kafkaPublisher;

    @GetMapping(value = "/")
    ResponseEntity<String> userService() {
        return ResponseEntity.ok("user-producer service");
    }

    @GetMapping("/health")
    ResponseEntity<String> health() {
        return new ResponseEntity<>("user producer service up and running...", HttpStatus.OK);
    }

    @PostMapping("/process")
    public ResponseEntity<DecisionResponse> processData(@RequestBody UserInfo userInfo) throws JsonProcessingException {
        log.info("------------userInfo : {}", userInfo);
        DecisionResponse response = new DecisionResponse();
        //add if condition in json payload to check whether it send to kafka consumer or decision service.

        if(userInfo.getRoutingIndicator().equals("USR-IND")){
            kafkaPublisher.kafkaPublisher(userInfo);
            log.info("message sent to user consumer service successfully");
        }else {
            /* @TODO */
            response = userHandlerService.decisionResponse(userInfo, response);
            log.info("user info data sent to decision service");
        }

        log.info("---------------userResponse : {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
