package com.hepsiburada.etl.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class NonPersonalizedRecommendationDto {

    private String rowLabel = "NON_PERSONALIZED";
    private int rowNum;
    private int salesCount;
    private String productId;
    private String categoryId;

}
