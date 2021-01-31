//package com.hepsiburada.viewproducer.launcher;
//
//import com.hepsiburada.viewproducer.component.ResettableCountDownLatch;
//import com.hepsiburada.viewproducer.service.KafkaProducerService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class KafkaProducerLauncher2 {
//
//    private final KafkaProducerService kafkaProducerService;
//
//    @Scheduled(cron = "* * 1 * * *")
//    public void read() throws Exception {
//        kafkaProducerService.produceMessages();
//    }
//
//}
