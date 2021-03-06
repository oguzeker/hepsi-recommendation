package com.hepsiburada.api.controller.impl;

import com.hepsiburada.api.controller.RecommendationController;
import com.hepsiburada.api.controller.response.RecommendationResponse;
import com.hepsiburada.api.service.impl.RecommendationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/recommendation")
public class RecommendationControllerImpl implements RecommendationController {

    private final RecommendationServiceImpl recommendationService;

    @GetMapping(value = "/userId/{userId}")
    public ResponseEntity<RecommendationResponse> getRecommendation(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(recommendationService.getRecommendation(userId));
    }

}
