package com.hepsiburada.streamreader.properties;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@Getter
@ConfigurationProperties(prefix = "spring.kafka")
public class SpringKafkaProperties {

    private String bootstrapServers;
    private Consumer consumer;

    @Data
    public static class Consumer {
        private String bootstrapServers;
        private String groupId;
        private String topicName;
    }
}
