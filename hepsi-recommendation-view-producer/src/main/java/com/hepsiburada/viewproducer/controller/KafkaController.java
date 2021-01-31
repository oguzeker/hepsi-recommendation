//package com.hepsiburada.viewproducer.controller;
//
//import com.hepsiburada.viewproducer.model.View;
//import com.hepsiburada.viewproducer.service.KafkaProducerService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
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
//        View view = new View("10",transactionType);
//        this.kafkaProducerService.send(view);
//    }
//
//}
