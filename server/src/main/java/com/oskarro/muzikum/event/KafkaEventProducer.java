package com.oskarro.muzikum.event;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class KafkaEventProducer<T> {

    private KafkaTemplate<String, BaseEvent<T>> kafkaTemplate;

    public void sendMessage(BaseEvent<T> event, String topic) {
        log.info("Base Event: {}", event.toString());

        Message<BaseEvent<T>> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .build();
        kafkaTemplate.send(message);
    }
}
