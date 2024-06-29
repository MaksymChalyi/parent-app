package com.maksimkaxxl.messagesendermicroservice.services.email.impl;

import com.maksimkaxxl.messagesendermicroservice.models.EmailMessage;
import com.maksimkaxxl.messagesendermicroservice.models.enums.EmailStatus;
import com.maksimkaxxl.messagesendermicroservice.repositories.EmailMessageRepository;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmailServiceImplTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private EmailMessageRepository emailMessageRepository;

    @InjectMocks
    private EmailServiceImpl emailService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendEmailMessage_Success() throws Exception {
        // Arrange
        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setRecipientEmail("recipient@example.com");
        emailMessage.setSubject("Test Subject");
        emailMessage.setContent("Test Content");

        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        doNothing().when(mailSender).send(any(MimeMessage.class));
        when(emailMessageRepository.save(any(EmailMessage.class))).thenReturn(emailMessage);

        // Act
        emailService.sendEmailMessage(emailMessage);

        // Assert
        assertEquals(EmailStatus.SENT, emailMessage.getEmailStatus());
        assertNull(emailMessage.getErrorMessage());
        verify(mailSender, times(1)).send(mimeMessage);
        verify(emailMessageRepository, times(1)).save(emailMessage);
    }

    @Test
    public void testSendEmailMessage_Failure() throws Exception {
        // Arrange
        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setRecipientEmail("recipient@example.com");
        emailMessage.setSubject("Test Subject");
        emailMessage.setContent("Test Content");

        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        doThrow(new RuntimeException("SMTP Error")).when(mailSender).send(any(MimeMessage.class));
        when(emailMessageRepository.save(any(EmailMessage.class))).thenReturn(emailMessage);

        // Act
        emailService.sendEmailMessage(emailMessage);

        // Assert
        assertEquals(EmailStatus.FAILED, emailMessage.getEmailStatus());
        assertEquals("RuntimeException: SMTP Error", emailMessage.getErrorMessage());
        verify(mailSender, times(1)).send(mimeMessage);
        verify(emailMessageRepository, times(1)).save(emailMessage);
    }
}