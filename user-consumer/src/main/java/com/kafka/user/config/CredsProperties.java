package com.kafka.user.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class CredsProperties {

    // tyk aric connection keystore pass
    @Value("${ssl.p12.password)")
    private String p12Password;

    // tyk aric connection truststore pass
    @Value("${ssl.p12.trustStorePassword}")
    private String trustStorePassword;

    // kafka broker keystore pass
    @Value("${spring.consumer.kafka.truststorePassword}")
    private String k4aKeyStorePass;

    // kafka broker truststore pass
    @Value("${spring.consumer.kafka.truststorePassword}")
    private String k4aTrustStorePass;

}
