package com.hepsiburada.api.service;

import com.hepsiburada.api.controller.response.RecommendationResponse;
import com.hepsiburada.api.entity.NonPersonalizedRecommendation;
import com.hepsiburada.api.entity.PersonalizedRecommendation;
import com.hepsiburada.api.mapper.RecommendationResponseMapper;
import com.hepsiburada.api.properties.RecommendationProperties;
import com.hepsiburada.api.repository.NonPersonalizedRecommendationRepository;
import com.hepsiburada.api.repository.PersonalizedRecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final PersonalizedRecommendationRepository personalizedRecommendationRepository;
    private final NonPersonalizedRecommendationRepository nonPersonalizedRecommendationRepository;
    private final RecommendationProperties recommendationProperties;

    public RecommendationResponse getUserBrowsingHistory(String userId) {
        RecommendationResponse recommendationResponse;
        List<PersonalizedRecommendation> personalizedRecommendationList = getPersonalizedRecommendations(userId);

        if (CollectionUtils.isEmpty(personalizedRecommendationList)) {
            recommendationResponse = RecommendationResponseMapper.mapNonPersonalizedRecommendations(userId,
                    getNonPersonalizedRecommendations());
        } else {
            recommendationResponse = RecommendationResponseMapper.mapPersonalizedRecommendations(userId,
                    personalizedRecommendationList);
        }

        performMinimumProductCountCheck(recommendationResponse);
        return recommendationResponse;
    }

    private void performMinimumProductCountCheck(RecommendationResponse recommendationResponse) {
        List<String> products = recommendationResponse.getProducts();
        if (products.size() < recommendationProperties.getMinNumberOfProducts()) {
            products.clear();
        }
    }

    private List<PersonalizedRecommendation> getPersonalizedRecommendations(String userId) {
        return personalizedRecommendationRepository.findByUserId(userId);
    }

    private List<NonPersonalizedRecommendation> getNonPersonalizedRecommendations() {
        return nonPersonalizedRecommendationRepository.findAll();
    }

}
