package com.maksimkaxxl.messagesendermicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MessageSenderMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessageSenderMicroserviceApplication.class, args);
    }

}
