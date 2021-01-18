package com.hepsiburada.etl.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class PersonalizedRecommendationDto {

    private String userId;
    private int rowNum;
    private int salesCount;
    private String productId;
    private String categoryId;

}
