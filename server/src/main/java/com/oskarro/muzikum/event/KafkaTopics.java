package com.oskarro.muzikum.event;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@ConfigurationProperties(prefix = "spring.kafka.topic")
public class KafkaTopics {

    @Value("${spring.kafka.topic.track.name}")
    private String topicTrackName;

    @Value("${spring.kafka.topic.user.name}")
    private String topicUserName;

}
