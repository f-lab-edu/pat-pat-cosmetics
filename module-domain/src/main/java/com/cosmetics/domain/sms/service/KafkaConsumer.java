package com.cosmetics.domain.sms.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = "#{topic1.name}", groupId = "group1")
    public void consume(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) throws IOException {
        //비즈니스 로직
        log.error("Consumer message: {}", message);
        //데이터 베이스 저장

        //예를들어 여기서 알림톡API를 호출하나요?

    }


}
