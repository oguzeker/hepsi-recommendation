package com.hepsiburada.streamreader.configuration;

import com.hepsiburada.streamreader.model.View;
import com.hepsiburada.streamreader.properties.SpringKafkaProperties;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfiguration {

    private final SpringKafkaProperties springKafkaProperties;

    @Bean
    public ConsumerFactory<String, View> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        SpringKafkaProperties.Consumer consumer = springKafkaProperties.getConsumer();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, consumer.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, consumer.getGroupId());
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(View.class,
                false));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, View> kafkaListenerContainerFactory(
            ConsumerFactory<String, View> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, View> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

}
