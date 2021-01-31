package com.hepsiburada.viewproducer.service;

import com.hepsiburada.viewproducer.properties.SpringKafkaProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaErrorProducerService extends AbstractKafkaProducerService<String> {

    private final SpringKafkaProperties springKafkaProperties;

    public KafkaErrorProducerService(KafkaTemplate<String, String> kafkaErrorTemplate,
                                     SpringKafkaProperties springKafkaProperties) {
        super(kafkaErrorTemplate);
        this.springKafkaProperties = springKafkaProperties;
    }

    public String getTopicName() {
        return springKafkaProperties.getProducer().getErrorTopic().getName();
    }

}
