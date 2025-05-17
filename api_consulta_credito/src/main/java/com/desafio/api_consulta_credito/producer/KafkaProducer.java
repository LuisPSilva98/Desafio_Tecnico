package com.desafio.api_consulta_credito.producer;

public interface KafkaProducer {
    void send(String message);
}
