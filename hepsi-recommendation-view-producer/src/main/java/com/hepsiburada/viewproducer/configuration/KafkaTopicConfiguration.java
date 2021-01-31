package com.hepsiburada.viewproducer.configuration;

import com.hepsiburada.viewproducer.properties.SpringKafkaProperties;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaTopicConfiguration {

    private final SpringKafkaProperties springKafkaProperties;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, springKafkaProperties.getBootstrapServers());
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topic() {
        SpringKafkaProperties.Producer.Topic topic = springKafkaProperties.getProducer().getTopic();
        return new NewTopic(topic.getName(), topic.getNumPartitions(), topic.getReplicationFactor());
    }

    @Bean
    public NewTopic errorTopic() {
        SpringKafkaProperties.Producer.ErrorTopic errorTopic = springKafkaProperties.getProducer().getErrorTopic();
        return new NewTopic(errorTopic.getName(), errorTopic.getNumPartitions(), errorTopic.getReplicationFactor());
    }

}
