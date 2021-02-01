package com.hepsiburada.api.service;

import com.hepsiburada.api.controller.response.RecommendationResponse;

public interface RecommendationService {
    RecommendationResponse getRecommendation(String userId);
}
