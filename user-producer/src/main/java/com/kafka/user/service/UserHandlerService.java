package com.kafka.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.user.config.KafkaProperties;
import com.kafka.user.model.UserInfo;
import com.kafka.user.model.UserRequest;
import com.kafka.user.model.UserResponse;
import com.kafka.user.transformation.TransformData;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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

    public UserResponse userResponse(UserInfo userInfo, UserResponse userResponse) throws JsonProcessingException {
        userResponseProcessData(userInfo, userResponse);
        UserRequest userRequest = new UserRequest();
        userRequest.userInfo = userInfo;
        userRequest.userResponse = userResponse;
        //send userRequest data to kafka
        kafkaTemplate.send(kafkaProperties.getTopic(),userRequest);
        log.info("send to user request data to kafka topic : " +kafkaProperties.getTopic()+" with payload :  "  + mapper.writeValueAsString(userRequest));
        return userResponse;
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
