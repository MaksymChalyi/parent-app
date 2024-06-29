package com.maksimkaxxl.messagesendermicroservice.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maksimkaxxl.messagesendermicroservice.models.EmailMessage;
import com.maksimkaxxl.messagesendermicroservice.services.email.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailSenderListener {

    private final EmailService emailService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${kafka.topic.mailSender}")
    public void employeeAdded(String jsonMessage) {
        log.info("Received message: {}", jsonMessage);
        try {
            EmailMessage emailMessage = objectMapper.readValue(jsonMessage, EmailMessage.class);
            log.info("Parsed EmailMessage: {}", emailMessage);
            emailService.sendEmailMessage(emailMessage);
            log.info("EmailMessage processed successfully");
        } catch (JsonProcessingException e) {
            log.error("Failed to parse JSON message: {}", jsonMessage, e);
        }
    }


}
