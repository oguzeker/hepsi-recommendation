package com.hepsiburada.viewproducer.properties;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@Getter
@ConfigurationProperties(prefix = "spring.kafka")
public class SpringKafkaProperties {

    private String bootstrapServers;
    private Producer producer;

    @Data
    public static class Producer {
        private String bootstrapServers;
        private Topic topic;
        private ErrorTopic errorTopic;

        @Data
        public static class Topic {
            private String name;
            private int numPartitions;
            private short replicationFactor;
        }

        @Data
        public static class ErrorTopic {
            private String name;
            private int numPartitions;
            private short replicationFactor;
        }
    }
}
