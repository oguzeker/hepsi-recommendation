package com.hepsiburada.api.mapper;

import com.hepsiburada.api.controller.response.RecommendationResponse;
import com.hepsiburada.api.entity.NonPersonalizedRecommendation;
import com.hepsiburada.api.entity.PersonalizedRecommendation;
import com.hepsiburada.api.model.RecommendationType;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class RecommendationResponseMapper {

    public RecommendationResponse mapPersonalizedRecommendations(
            String userId, List<PersonalizedRecommendation> personalizedRecommendationList) {
        RecommendationResponse recommendationResponse = RecommendationResponse.builder()
                .userId(userId)
                .recommendationType(RecommendationType.PERSONALIZED)
                .products(new ArrayList<>())
                .build();

        personalizedRecommendationList.forEach(personalizedRecommendation -> recommendationResponse.getProducts()
                .add(personalizedRecommendation.getProductId()));

        return recommendationResponse;
    }

    public RecommendationResponse mapNonPersonalizedRecommendations(
            String userId, List<NonPersonalizedRecommendation> nonPersonalizedRecommendationList) {
        RecommendationResponse recommendationResponse = RecommendationResponse.builder()
                .userId(userId)
                .recommendationType(RecommendationType.NON_PERSONALIZED)
                .products(new ArrayList<>())
                .build();

        nonPersonalizedRecommendationList.forEach(nonPersonalizedRecommendation -> recommendationResponse.getProducts()
                .add(nonPersonalizedRecommendation.getProductId()));

        return recommendationResponse;
    }

}
