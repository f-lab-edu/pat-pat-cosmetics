package com.cosmetics.domain.sms.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class SmsService {

    private final KafkaProducer kafkaProducer;

    @Async
    public void smsMessage(Long goodsNo) {
        //sms 보낸다고 가정
        String message = "[알림]상품이 정상적으로 생성되었습니다. 상품을 확인해 주세요." + goodsNo;
        kafkaProducer.sendMessage(message);
    }
}
