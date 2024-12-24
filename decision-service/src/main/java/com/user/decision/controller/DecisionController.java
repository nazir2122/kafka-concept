package com.user.decision.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.decision.config.MapperConfig;
import com.user.decision.model.DecisionResponse;
import com.user.decision.model.UserRequest;
import com.user.decision.service.DecisionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/decision/v1")
public class DecisionController {

    @Autowired
    ObjectMapper mapper;
    @Autowired
    DecisionService decisionService;
    @GetMapping(value = "/")
    ResponseEntity<String> userService() {
        return ResponseEntity.ok("user-producer service");
    }

    @GetMapping("/health")
    ResponseEntity<String> health() {
        return new ResponseEntity<>("user producer service up and running...", HttpStatus.OK);
    }

    @PostMapping(value = "/process")
    public ResponseEntity<DecisionResponse> userProcessDecision(@RequestBody UserRequest userRequest) throws JsonProcessingException {
        DecisionResponse decisionResponse=new DecisionResponse();
        decisionService.decisionResponse(userRequest,decisionResponse);
        log.info("decision response values :{}", mapper.writeValueAsString(decisionResponse));
        return new ResponseEntity<>(decisionResponse, HttpStatus.OK);
    }
}
