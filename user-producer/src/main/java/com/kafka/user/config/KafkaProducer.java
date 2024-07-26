package com.kafka.user.config;

import com.kafka.user.model.UserInfo;
import com.kafka.user.model.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class KafkaProducer {

    @Autowired
    @Qualifier("kafkaProperties")
    KafkaProperties props;
    @Autowired
    AppProperties appProperties;
    @Autowired
   CredsProperties credsProperties;

    @Bean
    public NewTopic createUserTopic(){
        return new NewTopic(props.topic,3,(short)1);
    }
//    @Primary
//    @Qualifier("kafkaTemplate")
    @Bean("kafkaTemplate")
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> configMap = new HashMap<>();
        configMap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configMap.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configMap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//        try {
//            if (props.isSslEnable() && StringUtils.isNotBlank(props.getK4aTrustStoreLocation()) && org.apache.commons.lang3.StringUtils.isNotBlank(props.getK4aKeyStoreLocation()) && StringUtils.isNotBlank(credsProperties.getK4aKeyStorePass()) && StringUtils.isNotBlank(credsProperties.getK4aKeyStorePass())){
//                configMap.put(SslConfigs.SSL_TRUSTSTORE_TYPE_CONFIG,appProperties.getTrustKeystoreType());
//                configMap.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG,getAbsolutePath(props.getK4aTrustStoreLocation()));
//                configMap.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG,credsProperties.getK4aTrustStorePass());
//                configMap.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG,appProperties.getSecurityProtocol());
//                configMap.put(SslConfigs.SSL_KEYSTORE_TYPE_CONFIG, appProperties.getTrustKeystoreType());
//                configMap.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, getAbsolutePath(props.getK4aKeyStoreLocation()));
//                configMap.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, credsProperties.getK4aKeyStorePass());
//                configMap.put(ProducerConfig.CLIENT_ID_CONFIG, props.getClientid());
//                configMap.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, props.getMaxBlockMs());
//                configMap.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, props.getRequestTimeout());
//
//                configMap.put(ProducerConfig.RETRIES_CONFIG, props.getMaxRetry());
//                configMap.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, props.getRetryBackoff());
//                configMap.put(ProducerConfig.ACKS_CONFIG, props.getAcks());
//                configMap.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, props.getInFlightMessages());
//                configMap.put(ProducerConfig.BATCH_SIZE_CONFIG, props.getBatchSize());
//                configMap.put(ProducerConfig.LINGER_MS_CONFIG, props.getLingerMs());
//                configMap.put(ProducerConfig.BUFFER_MEMORY_CONFIG, props.getBufferMemory());
//                configMap.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, props.getIdempotenceConfig());
//                configMap.put(ProducerConfig.METADATA_MAX_IDLE_CONFIG, props.getMetadataMaxIdleConfig());
//            }else{
//                log.error("SSL is not enable for kafka");
//            }
//        }catch (Exception ex){
//            log.error("producer factory configuration error");
//            ex.printStackTrace();
//        }
        return new DefaultKafkaProducerFactory<>(configMap);
    }

//    public  String getAbsolutePath(String resources){
//        File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(resources)).getFile());
//        return  file.getAbsolutePath();
//    }
}
