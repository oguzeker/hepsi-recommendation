package com.hepsiburada.streamreader.service;

import com.hepsiburada.streamreader.model.View;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumerService {

//    @KafkaListener(topics = "${spring.kafka.consumer.topic}",
//            groupId = "${spring.kafka.consumer.group-id}",
//            containerFactory = "kafkaListenerContainerFactory",
//            autoStartup = "${spring.kafka.consumer.enable}")
//
    @KafkaListener(topics = "${spring.kafka.consumer.topic-name}",
            containerFactory = "kafkaListenerContainerFactory",
            groupId = "${spring.kafka.consumer.group-id}",
            autoStartup = "${spring.kafka.consumer.enabled}")
    public void consume(@Payload View view,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partition,
                        @Header(KafkaHeaders.OFFSET) Long offset,
                        @Header(KafkaHeaders.RECEIVED_TOPIC) String topicName) {
        log.info("Consumed message with topicName: {}, and partition: {}, and offset: {}, {}",
                topicName, partition, offset, view);
//        System.out.println(" >>> Consumed msg: " + view.toString());
    }

}
