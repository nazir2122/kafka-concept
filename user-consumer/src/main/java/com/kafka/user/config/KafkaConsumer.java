package com.kafka.user.config;

import com.kafka.user.model.UserInfo;
import com.kafka.user.model.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Configuration
public class KafkaConsumer {
    @Autowired
    KafkaProperties kafkaProperties;
    @Autowired
    CredsProperties credsProperties;
    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> configs = new HashMap<>();
        try {
            configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            configs.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getConsumerGroupid());
            configs.put(ConsumerConfig.CLIENT_ID_CONFIG, kafkaProperties.getConsumerClientid());
            configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
            configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,JsonDeserializer.class);
//            configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class.getName());
//            configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class.getName());
//            configs.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class.getName());
//            configs.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, UserRequest.class.getName());
            configs.put(JsonDeserializer.VALUE_DEFAULT_TYPE, UserInfo.class.getName());
            configs.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
            configs.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, kafkaProperties.getFetchMinBytes());
            configs.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, kafkaProperties.getFetchMaxWaitMs());
            configs.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, kafkaProperties.getMaxPollRecords());
            configs.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, kafkaProperties.getEnableAutoCommit());
            configs.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, kafkaProperties.getConsumerMaxPollIntervalMs().intValue());
            configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaProperties.getConfigOffset());
            if ((kafkaProperties.getEnableSsl()).equalsIgnoreCase("Y")) {
                configs.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, credsProperties.getK4aTrustStorePass());
                configs.put(SslConfigs.SSL_TRUSTSTORE_TYPE_CONFIG, kafkaProperties.getConfigType());
                configs.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, getAbsolutePath(kafkaProperties.getTruststoreLocation()));
                configs.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, kafkaProperties.getSsl());
                configs.put(SslConfigs.SSL_KEYSTORE_TYPE_CONFIG, kafkaProperties.getConfigType());
                configs.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, getAbsolutePath(kafkaProperties.getKeystoreLocation()));
                configs.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, credsProperties.getK4aKeyStorePass());
            } else {
                log.error("Kafka SSL not available for service");
            }
        } catch (Exception ex) {
            log.error("Kafka configuration error");
        }
        return new DefaultKafkaConsumerFactory<>(configs);
    }

    public String getAbsolutePath(String resource) {
        File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(resource)).getFile());
        return file.getAbsolutePath();
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserRequest> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, UserRequest> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setCommonErrorHandler(errorHandler());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        factory.setRecordMessageConverter(new StringJsonMessageConverter());
        factory.setBatchListener(true);
        return factory;
    }


    @Bean
    public DefaultErrorHandler errorHandler() {
        BackOff fixedBackOff = new FixedBackOff(kafkaProperties.getConsumerRetryBackoff(), Long.valueOf(kafkaProperties.getConsumerRetryCount()));
        DefaultErrorHandler errorHandler = new DefaultErrorHandler((rec, ex) -> {
            log.error(" Error occurred while transferring record with Card Ref Id: after attempting + kafkaProperties.getConsumerRetryCount() + times in" + kafkaProperties.getConsumerRetryBackoff() + " interval Message: " + ex.getMessage());
            ;
            // TokenNRTScheduler.turnOnAricHeartBeatCheckScheduler();
        }, fixedBackOff);
        errorHandler.setAckAfterHandle(false);
        errorHandler.addRetryableExceptions(Exception.class);
        return errorHandler;
    }
}
