package com.hepsiburada.etl;

import com.hepsiburada.etl.configuration.properties.ApplicationProperties;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableBatchProcessing
@SpringBootApplication
@EnableConfigurationProperties({
        ApplicationProperties.class
})
public class HepsiRecommendationEtlApplication {

    public static void main(String[] args) {
        SpringApplication.run(HepsiRecommendationEtlApplication.class, args);
    }
}
