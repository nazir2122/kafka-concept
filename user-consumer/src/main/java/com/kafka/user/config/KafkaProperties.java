package com.kafka.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.consumer.kafka")
public class KafkaProperties {
    private String bootStrapServer;
    private String consumerClientid;
    private String consumerGroupid;
    private String groupId;
    private String producerClientid;
    private String ssl;
    private String truststoreLocation;
    private String keystoreLocation;
    private String configType;
    private String configOffset;
    private String configAcks;
    private Integer fetchMinBytes;
    private Integer fetchMaxWaitMs;
    private Integer maxPollRecords;
    private String enableAutoCommit;
    private String enableSsl;
    private String idempotenceConfig;
    private String maxRetry;
    private String deliveryTimeout;
    private String maxBlockMs;
    private String inFlightMessages;
    private Long consumerRetryBackoff;
    private Integer consumerRetryCount;
    private Long consumerMaxPollIntervalMs;
    private Long consumerWaitTime;
    private String partAssignmentStrategy;
}
