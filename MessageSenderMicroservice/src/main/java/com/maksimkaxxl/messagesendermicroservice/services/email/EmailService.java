package com.maksimkaxxl.messagesendermicroservice.services.email;

import com.maksimkaxxl.messagesendermicroservice.models.EmailMessage;

public interface EmailService {

    void sendEmailMessage(EmailMessage emailMessage);

}
