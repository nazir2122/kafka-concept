package com.kafka.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.user.config.KafkaProperties;
import com.kafka.user.model.DecisionResponse;
import com.kafka.user.model.UserInfo;
import com.kafka.user.model.UserRequest;
import com.kafka.user.model.UserResponse;
import com.kafka.user.transformation.TransformData;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Collections;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserHandlerService {
    @Autowired
    TransformData transformData;
    @Autowired
    ObjectMapper mapper;
    @Autowired
  KafkaProperties kafkaProperties;
    @Autowired
  KafkaTemplate<String,Object> kafkaTemplate;
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    KafkaPublisher kafkaPublisher;
    private HttpEntity<UserRequest> frameRequest(UserRequest userRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return new HttpEntity<>(userRequest, headers);
    }

    public DecisionResponse decisionResponse(UserInfo userInfo, DecisionResponse response) throws JsonProcessingException {
        UserResponse userResponse=new UserResponse();
        userResponseProcessData(userInfo, userResponse);
        UserRequest userRequest = new UserRequest();
        userRequest.userInfo = userInfo;
        userRequest.userResponse = userResponse;
        HttpEntity<UserRequest> requestEntity = frameRequest(userRequest);
        //send userRequest data to decision service else kafka
        try{
            //implement later
            //send userRequest data to decision service
            response = restTemplate.exchange("http://localhost:8083/decision/v1/process", HttpMethod.POST, requestEntity, DecisionResponse.class).getBody();
            log.info("UserRequest Data sent to decision service: {}", mapper.writeValueAsString(userRequest));

        }
        catch (Exception ex){
            kafkaPublisher.kafkaPublisher(userInfo);
             throw ex;
        }


        return response;
    }

    private UserResponse userResponseProcessData(UserInfo userInfo, UserResponse userResponse) {
        String eventStatus = transformData.eventStatus(userInfo);
        userResponse.eventStatus = eventStatus;
        log.info("event status : {} user age : {}", eventStatus, userInfo.userAge);
        String userName = transformData.userName(userInfo);
        userResponse.userName = userName;
        log.info("user full name is : {}", userName);
        String uniqueEventId = transformData.uniqueEventId(userInfo).toUpperCase();
        userResponse.uniqueEventId = uniqueEventId;
        log.info("unique event id : {}", uniqueEventId);
        LocalDate eventTime = LocalDate.now();
        userResponse.eventTime = eventTime;
        log.info("event date : {}", eventTime);
        return userResponse;
    }
}
