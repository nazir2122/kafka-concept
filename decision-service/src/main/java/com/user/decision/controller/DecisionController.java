package com.user.decision.controller;

import com.user.decision.model.DecisionResponse;
import com.user.decision.model.UserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/decision/v1")
public class DecisionController {

    @GetMapping(value = "/")
    ResponseEntity<String> userService() {
        return ResponseEntity.ok("user-producer service");
    }

    @GetMapping("/health")
    ResponseEntity<String> health() {
        return new ResponseEntity<>("user producer service up and running...", HttpStatus.OK);
    }

    @PostMapping(value = "/process")
    public ResponseEntity<DecisionResponse> userProcessDecision(@RequestBody UserRequest userRequest){
        DecisionResponse decisionResponse=null;
        return new ResponseEntity<>(decisionResponse, HttpStatus.OK);
    }
}
