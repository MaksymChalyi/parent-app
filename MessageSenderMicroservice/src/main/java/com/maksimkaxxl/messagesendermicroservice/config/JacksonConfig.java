package com.maksimkaxxl.messagesendermicroservice.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        log.info("Initializing ObjectMapper");

        ObjectMapper mapper = new ObjectMapper();

        // Не створювати exception, якщо json має додаткові поля без серіалізації
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        log.info("Configured ObjectMapper to ignore unknown properties");

        // Ігнорувати нульові значення під час запису json
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        log.info("Configured ObjectMapper to exclude null values during serialization");

        return mapper;
    }
}
