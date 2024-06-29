package com.maksimkaxxl.messagesendermicroservice.services.email.impl;

import com.maksimkaxxl.messagesendermicroservice.models.EmailMessage;
import com.maksimkaxxl.messagesendermicroservice.models.enums.EmailStatus;
import com.maksimkaxxl.messagesendermicroservice.repositories.EmailMessageRepository;
import com.maksimkaxxl.messagesendermicroservice.services.email.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final EmailMessageRepository emailMessageRepository;

    @Override
    public void sendEmailMessage(EmailMessage emailMessage) {
        log.info("Attempting to send email to {}", emailMessage.getRecipientEmail());
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(emailMessage.getRecipientEmail());
            helper.setSubject(emailMessage.getSubject());
            helper.setText(emailMessage.getContent(), true);
            mailSender.send(mimeMessage);
            emailMessage.setErrorMessage(null);
            emailMessage.setEmailStatus(EmailStatus.SENT);
            log.info("Email sent successfully to {}", emailMessage.getRecipientEmail());
        } catch (Exception e) {
            emailMessage.setEmailStatus(EmailStatus.FAILED);
            emailMessage.setErrorMessage(e.getClass().getSimpleName() + ": " + e.getMessage());
            log.error("Failed to send email to {}: {}", emailMessage.getRecipientEmail(), e.getMessage());
        }

        emailMessage.setLastAttemptTime(Instant.now());
        emailMessage.setRetryCount(Optional.of(emailMessage.getRetryCount()).orElse(0) + 1);
        emailMessageRepository.save(emailMessage);
        log.info("Email message status updated in database: {}", emailMessage);
    }


    @Scheduled(fixedRate = 300_000)
    public void retryFailedEmails() {
        log.info("Retrying failed emails...");
        List<EmailMessage> failedMessages = emailMessageRepository.findByEmailStatus(EmailStatus.FAILED);
        log.info("Found {} failed emails to retry", failedMessages.size());
        for (var emailMessage : failedMessages) {
            log.info("Retrying email to {}", emailMessage.getRecipientEmail());
            sendEmailMessage(emailMessage);
        }
    }

}
