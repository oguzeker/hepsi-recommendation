package com.hepsiburada.api;

import com.hepsiburada.api.properties.RecommendationProperties;
import com.hepsiburada.api.properties.SwaggerProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.CrossOrigin;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@CrossOrigin
@EnableSwagger2
@SpringBootApplication
@EnableConfigurationProperties({
        RecommendationProperties.class,
        SwaggerProperties.class
})
public class HepsiRecommendationApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HepsiRecommendationApiApplication.class, args);
    }
}
