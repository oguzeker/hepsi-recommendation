package com.hepsiburada.viewproducer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
public abstract class AbstractKafkaProducerService<T> {

    @Value("classpath:product-views.json")
    protected Resource resource;
    private final KafkaTemplate<String, T> kafkaTemplate;

    protected AbstractKafkaProducerService(KafkaTemplate<String, T> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    void produce(T message) {
        String topicName = getTopicName();

        kafkaTemplate.send(topicName, message);
        log.info("Message sent, topicName: {}, message: {},", topicName, message);
    }

    public abstract String getTopicName();
}
