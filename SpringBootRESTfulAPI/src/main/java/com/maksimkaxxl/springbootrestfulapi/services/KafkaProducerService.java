package com.maksimkaxxl.springbootrestfulapi.services;

public interface KafkaProducerService {

    void sendMessage(String json);
}
