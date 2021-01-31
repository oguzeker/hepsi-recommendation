package com.hepsiburada.api;

import com.hepsiburada.api.properties.RecommendationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        RecommendationProperties.class
})
public class HepsiRecommendationApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HepsiRecommendationApiApplication.class, args);
    }
}
