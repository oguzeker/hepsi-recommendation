//package com.hepsiburada.streamreader.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping(value = "/kafka")
//public class KafkaController {
//
//    private final KafkaProducerService kafkaProducerService;
//
//    @Autowired
//    public KafkaController(KafkaProducerService kafkaProducerService) {
//        this.kafkaProducerService = kafkaProducerService;
//
//    }
//    @GetMapping(value = "/publish/{transactionType}")
//    public void sendTransactionTypeToKafkaTopic(@PathVariable("transactionType") String transactionType){
//        Transaction transaction = new Transaction("10",transactionType);
//        this.kafkaProducerService.send(transaction);
//    }
//
//}
