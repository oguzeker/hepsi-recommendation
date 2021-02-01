package com.hepsiburada.api;

import com.hepsiburada.api.entity.NonPersonalizedRecommendation;
import com.hepsiburada.api.entity.PersonalizedRecommendation;

public class TestBase {

    public static final String USER_ID = "user-101";
    public static final int MIN_NUMBER_OF_PRODUCTS = 5;
    public static final long ID = 1L;
    public static final String CATEGORY = "category-101";
    public static final String PRODUCT_ID = "product-101";
    public static final int SALES_COUNT = 1;
    public static final int ROW_NUM = 1;
    public static final String NON_PERSONALIZED = "NON_PERSONALIZED";

    public PersonalizedRecommendation createPersonalizedRecommendation() {
        return PersonalizedRecommendation.builder()
                .id(ID)
                .userId(null)
                .categoryId(CATEGORY)
                .productId(PRODUCT_ID)
                .rowNum(ROW_NUM)
                .salesCount(SALES_COUNT)
                .build();
    }

    public NonPersonalizedRecommendation createNonPersonalizedRecommendation() {
        return NonPersonalizedRecommendation.builder()
                .id(ID)
                .rowLabel(NON_PERSONALIZED)
                .categoryId(CATEGORY)
                .productId(PRODUCT_ID)
                .rowNum(ROW_NUM)
                .salesCount(SALES_COUNT)
                .build();
    }

}
