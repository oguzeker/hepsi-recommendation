package com.hepsiburada.streamreader.service;

import com.hepsiburada.streamreader.entity.ProductView;
import com.hepsiburada.streamreader.model.View;
import com.hepsiburada.streamreader.repository.ProductViewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final ProductViewRepository productViewRepository;

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

        productViewRepository.save(ProductView.builder()
                .userId(view.getUserId())
                .sourceType(view.getContext().getSourceType())
                .messageId(view.getMessageId())
                .productId(view.getProperties().getProductId())
                .build());
//        System.out.println(" >>> Consumed msg: " + view.toString());
    }

}
