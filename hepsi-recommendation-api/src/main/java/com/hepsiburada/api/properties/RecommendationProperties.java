package com.hepsiburada.api.properties;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@Getter
@ConfigurationProperties(prefix = "recommendation")
public class RecommendationProperties {
    private int minNumberOfProducts;
}
