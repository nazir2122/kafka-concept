package com.kafka.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.user.config.KafkaProperties;
import com.kafka.user.model.UserInfo;
import com.kafka.user.transformation.TransformData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaPublisher {
    @Autowired
    TransformData transformData;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    KafkaProperties kafkaProperties;
    @Autowired
    KafkaTemplate<String,Object> kafkaTemplate;

    public void kafkaPublisher(UserInfo userInfo) throws JsonProcessingException {
        kafkaTemplate.send(kafkaProperties.getTopic(),userInfo);
        log.info("send to user request data to kafka topic : {} with payload :  {}", kafkaProperties.getTopic(), mapper.writeValueAsString(userInfo));
    }
}
