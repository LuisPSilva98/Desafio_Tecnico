package com.desafio.api_consulta_credito.producer.impl;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.desafio.api_consulta_credito.producer.KafkaProducer;

@Service
public class KafkaProducerImpl implements KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final NewTopic creditoRequestLog;

    public KafkaProducerImpl(KafkaTemplate<String, String> kafkaTemplate, NewTopic creditoRequestLog) {
        this.kafkaTemplate = kafkaTemplate;
        this.creditoRequestLog = creditoRequestLog;
    }

    @Override
    public void send(String message) {
        kafkaTemplate.send(creditoRequestLog.name(), message);
    }
}
