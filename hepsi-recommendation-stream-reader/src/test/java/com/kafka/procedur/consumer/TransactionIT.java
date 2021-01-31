//package com.hepsiburada.streamreader;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.junit.ClassRule;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.listener.ContainerProperties;
//import org.springframework.kafka.listener.KafkaMessageListenerContainer;
//import org.springframework.kafka.listener.MessageListener;
//import org.springframework.kafka.test.context.EmbeddedKafka;
//import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
//import org.springframework.kafka.test.utils.ContainerTestUtils;
//import org.springframework.kafka.test.utils.KafkaTestUtils;
//
//import java.util.Map;
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.LinkedBlockingQueue;
//import java.util.concurrent.TimeUnit;
//
//import static org.junit.Assert.assertThat;
//import static org.springframework.kafka.test.hamcrest.KafkaMatchers.hasValue;
//
//
//@SpringBootTest
//@EmbeddedKafka
//public class TransactionIT {
//    private static final String TOPIC = "TransactionEvent";
//    @Autowired
//    private  KafkaProducerService kafkaProducerService;
//
//
//    private  BlockingQueue<ConsumerRecord<String, String>> records;
//
//    private  KafkaMessageListenerContainer<String,Transaction> container;
//
//    @ClassRule
//    public static EmbeddedKafkaRule embeddedKafka = new EmbeddedKafkaRule(1, true, TOPIC);
//
//    @BeforeEach
//    public void setUp() {
//        records = new LinkedBlockingQueue<>();
//        ContainerProperties containerProperties = new ContainerProperties(TOPIC);
//        Map<String, Object> consumerProperties = KafkaTestUtils.consumerProps(
//                "sender", "false", embeddedKafka.getEmbeddedKafka());
//        DefaultKafkaConsumerFactory<String, Transaction> consumer = new DefaultKafkaConsumerFactory<String, Transaction>(consumerProperties);
//
//        container = new KafkaMessageListenerContainer<>(consumer, containerProperties);
//        container.setupMessageListener((MessageListener<String, String>) record -> {
//            if (record.topic().equals(TOPIC)){
//                records.add(record);
//            }
//            container.start();
//            ContainerTestUtils.waitForAssignment(container, embeddedKafka.getEmbeddedKafka().getPartitionsPerTopic());
//        });
//    }
//    @AfterEach
//    public void tearDown() {
//        container.stop();
//    }
//    @Test
//    public void sentMessage() throws InterruptedException, JsonProcessingException {
//        Transaction message = Transaction.builder().amount("10").transactionType("Auth").build() ;
//        kafkaProducerService.send(message);
//
//            // todo -> received is null.
//        ConsumerRecord<String, String> received = records.poll(10, TimeUnit.SECONDS);
//        ObjectMapper mapper = new ObjectMapper();
//        String json = mapper.writeValueAsString(message);
//        assertThat(received, hasValue(json));
//
//    }
//
//}
