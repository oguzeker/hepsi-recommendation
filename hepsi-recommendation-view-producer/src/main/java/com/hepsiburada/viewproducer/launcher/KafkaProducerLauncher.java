//package com.hepsiburada.viewproducer.launcher;
//
//import com.hepsiburada.viewproducer.component.ResettableCountDownLatch;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class KafkaProducerLauncher {
//
//    private final ResettableCountDownLatch resettableCountDownLatch;
//
//    @Scheduled(initialDelay = 100, fixedDelay = 1000)
////    @Scheduled(cron = "1/1 * * * * *")
//    public void readFileAndProduceMessage() {
//        resettableCountDownLatch.countDown();
//        resettableCountDownLatch.reset();
//    }
//
//}
