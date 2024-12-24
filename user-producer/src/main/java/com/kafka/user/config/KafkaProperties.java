package com.kafka.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "spring.kafka.producer")
@Configuration
public class KafkaProperties {
    /**
     * Bootstrap Server
     */
    protected String bootstrapServers;
    /**
     * SSL Flag
     */
    protected boolean sslEnable;
    /**
     * TrustStore Location
     */
    protected String trustStoreLocation;
    /**
     * K4a keyStore location
     */
    protected String k4aKeyStoreLocation;
    /**
     * k4a TrustStore Location
     */
    protected String k4aTrustStoreLocation;
    /**
     * broker ack mode
     */
    protected String acks;
    /**
     * kafka client id
     */
    protected String clientid;
    /**
     * bIdempotent producer config
     */
    protected String idempotenceConfig;
    /**
     * InFlight request per connection
     */
    protected String inFlightMessages;
    /**
     * block if partition is full
     */
    protected String maxBlockMs;
    /**
     * producer to wait for a response to get timeout exp
     */
    protected String requestTimeout;
    /**
     * Maximum number of retries
     */
    protected String maxRetry;
    /**
     * control the time delay between retries
     */
    protected String retryBackoff;
    /**
     * Batch size limit
     */
    protected String batchSize;
    /**
     * wait for the batch size if not yet full
     */
    protected String lingerMs;
    protected String bufferMemory;
    protected String topic;
    protected String metadataMaxIdleConfig;
}
