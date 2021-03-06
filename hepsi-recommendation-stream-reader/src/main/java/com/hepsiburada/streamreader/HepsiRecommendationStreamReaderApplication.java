package com.hepsiburada.streamreader;

import com.hepsiburada.streamreader.properties.SpringKafkaProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@EnableJpaAuditing
@SpringBootApplication
@EnableConfigurationProperties({
		SpringKafkaProperties.class
})
public class HepsiRecommendationStreamReaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(HepsiRecommendationStreamReaderApplication.class, args);
	}

}
