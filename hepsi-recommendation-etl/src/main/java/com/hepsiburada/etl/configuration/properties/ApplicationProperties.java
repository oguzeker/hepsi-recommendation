package com.hepsiburada.etl.configuration.properties;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@Getter
@ConfigurationProperties(prefix = "recommendation")
public class ApplicationProperties {

    private int pageSize;
    private int personalizedRecommendationsChunkSize;
    private int userPreferredCategoryLimit;
    private int categoryProductLimit;

}
