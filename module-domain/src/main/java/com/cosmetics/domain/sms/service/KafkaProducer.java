package com.cosmetics.domain.sms.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class KafkaProducer {

    private final NewTopic topic1;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        log.error("Producer topicName : {} / message : {}", topic1.name(), message);
        this.kafkaTemplate.send(topic1.name(), message);
    }
}
