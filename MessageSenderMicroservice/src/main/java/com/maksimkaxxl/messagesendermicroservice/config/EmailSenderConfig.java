package com.maksimkaxxl.messagesendermicroservice.config;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Objects;
import java.util.Properties;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class EmailSenderConfig {

    private final Dotenv dotenv;

    @Bean
    public JavaMailSender getJavaMailSender() {
        log.info("Initializing JavaMailSender with configuration from .env file");

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(dotenv.get("EMAIL_HOST"));
        mailSender.setPort(Integer.parseInt(Objects.requireNonNull(dotenv.get("EMAIL_PORT"))));
        log.info("Email host set to {}", dotenv.get("EMAIL_HOST"));
        log.info("Email port set to {}", dotenv.get("EMAIL_PORT"));

        mailSender.setUsername(dotenv.get("EMAIL_USERNAME"));
        mailSender.setPassword(dotenv.get("EMAIL_PASSWORD"));
        log.info("Email username set to {}", dotenv.get("EMAIL_USERNAME"));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.info", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        log.info("JavaMailSender properties configured");

        return mailSender;
    }
}
