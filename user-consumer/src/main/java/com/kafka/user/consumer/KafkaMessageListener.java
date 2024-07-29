package com.kafka.user.consumer;

import com.kafka.user.model.UserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaMessageListener {

    @KafkaListener(id = "${spring.consumer.kafka.consumerClientid)", topics = "user.topic", groupId = "${spring.consumer.kafka.consumerGroupid)", containerFactory = "kafkaListenerContainerFactory")
    public void processingBatch(List<UserRequest> allRequests,
                                @Header(KafkaHeaders.OFFSET) List<Long> offset,
                                @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                Acknowledgment acknowledgement) throws Exception {

        log.info("KafkaMessageListener: processingBatch:: Received Request with topic Value" + topic);
        log.info("KafkaMessageListener: processingBatch:: Received Request with Offset Value " + offset);
        log.info("KafkaMessageListener: processingBatch:: Batch list size + offset):" + allRequests.size());

        for (UserRequest request : allRequests) {
            if (request != null) {
                log.info("request value : " + request);
            } else {
                log.error("Committing offset as the transaction received is null due to Json payload formats");
                acknowledgement.acknowledge();
                log.info("KafkaMessageListener: kafkaAcknowledgement: ***Offset committed, Acknowledgment Sent");
            }
        }
    }
}
