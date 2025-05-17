package com.desafio.api_consulta_credito.producer.impl;

import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.*;

class KafkaProducerImplTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private NewTopic creditoRequestLog;

    private KafkaProducerImpl kafkaProducer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        kafkaProducer = new KafkaProducerImpl(kafkaTemplate, creditoRequestLog);
    }

    @Test
    void testSend_shouldCallKafkaTemplateSendWithCorrectTopicAndMessage() {
        // Arrange
        String topicName = "logs.creditos";
        String message = "mensagem de teste";

        when(creditoRequestLog.name()).thenReturn(topicName);

        // Act
        kafkaProducer.send(message);

        // Assert
        verify(kafkaTemplate, times(1)).send(topicName, message);
    }
}
