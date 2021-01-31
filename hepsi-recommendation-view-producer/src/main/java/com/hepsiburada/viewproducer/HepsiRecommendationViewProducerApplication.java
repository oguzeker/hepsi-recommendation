package com.hepsiburada.viewproducer;

import com.hepsiburada.viewproducer.properties.SpringKafkaProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableKafka
@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties({
		SpringKafkaProperties.class
})
public class HepsiRecommendationViewProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HepsiRecommendationViewProducerApplication.class, args);
	}

}
