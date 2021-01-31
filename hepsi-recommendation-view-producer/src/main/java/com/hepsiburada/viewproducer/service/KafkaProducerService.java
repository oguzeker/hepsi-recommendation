package com.hepsiburada.viewproducer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hepsiburada.viewproducer.model.View;
import com.hepsiburada.viewproducer.properties.SpringKafkaProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

@Service
public class KafkaProducerService extends AbstractKafkaProducerService<View> {

    private final KafkaErrorProducerService kafkaErrorProducerService;
    private final SpringKafkaProperties springKafkaProperties;
    private final ObjectMapper objectMapper;

    public KafkaProducerService(KafkaTemplate<String, View> kafkaTemplate,
                                KafkaErrorProducerService kafkaErrorProducerService,
                                SpringKafkaProperties springKafkaProperties,
                                ObjectMapper objectMapper) {
        super(kafkaTemplate);
        this.kafkaErrorProducerService = kafkaErrorProducerService;
        this.objectMapper = objectMapper;
        this.springKafkaProperties = springKafkaProperties;
    }

    @PostConstruct
    public void produceMessages() {
        String line = StringUtils.EMPTY;
        try(BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            line = br.readLine();
            while (Objects.nonNull(line)) {
                View view = objectMapper.readValue(line, View.class);
                produce(view);
                Thread.sleep(1000);

                line = br.readLine();
            }
        } catch (IOException | InterruptedException e) {
            kafkaErrorProducerService.produce(line);
            e.printStackTrace();
        }
    }

    public String getTopicName() {
        return springKafkaProperties.getProducer().getTopic().getName();
    }

}
