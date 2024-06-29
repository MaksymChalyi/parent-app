package com.maksimkaxxl.messagesendermicroservice.config;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class DotenvConfig {

    @Bean
    public Dotenv dotenv() {
        log.info("Loading environment variables from .env file");
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

        if (dotenv.entries().isEmpty()) {
            log.warn("No environment variables found or .env file is missing");
        } else {
            log.info("Environment variables loaded successfully");
        }

        return dotenv;
    }
}
