package com.kafka.user.consumer;

import com.kafka.user.model.UserInfo;
import com.kafka.user.model.UserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
//@PropertySource("classpath:application.properties")
public class KafkaMessageListener {

    @KafkaListener(id = "${spring.consumer.kafka.consumerClientid}", topics = "${spring.consumer.kafka.topic}", groupId = "${spring.consumer.kafka.consumerGroupid}", containerFactory = "kafkaListenerContainerFactory")
    public void processingBatch(List<UserInfo> allUsers,
                                @Header(KafkaHeaders.OFFSET) List<Long> offset,
                                @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                Acknowledgment acknowledgement) throws Exception {

        log.info("KafkaMessageListener: processingBatch:: Received Request with topic Value {}", topic);
        log.info("KafkaMessageListener: processingBatch:: Received Request with Offset Value {}", offset);
        log.info("KafkaMessageListener: processingBatch:: Batch list size + offset : {}", allUsers.size());

        for (UserInfo user : allUsers) {
            if (user != null) {
                log.info("request value : {}", user);
                acknowledgement.acknowledge();
                log.info("Acknowledgment successfully");
            } else {
                log.error("Committing offset as the transaction received is null due to Json payload formats");
                //acknowledgement.acknowledge();
                log.info("KafkaMessageListener: kafkaAcknowledgement: ***Offset committed, Acknowledgment Sent");
            }
        }

    }
}
