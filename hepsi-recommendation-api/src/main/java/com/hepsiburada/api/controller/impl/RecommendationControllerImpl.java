package com.hepsiburada.api.controller.impl;

import com.hepsiburada.api.controller.RecommendationController;
import com.hepsiburada.api.controller.response.RecommendationResponse;
import com.hepsiburada.api.service.BrowsingHistoryService;
import com.hepsiburada.api.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/recommendation")
public class RecommendationControllerImpl implements RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping(value = "/userId/{userId}")
    public ResponseEntity<RecommendationResponse> getRecommendation(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(recommendationService.getUserBrowsingHistory(userId));
//        recommendationService.getUserBrowsingHistory();

//        Transaction transaction = new Transaction("10",transactionType);
//        this.kafkaProducerService.send(transaction);
    }

}
