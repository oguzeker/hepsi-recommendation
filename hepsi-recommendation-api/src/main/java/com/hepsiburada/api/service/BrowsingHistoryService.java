package com.hepsiburada.api.service;

import com.hepsiburada.api.controller.response.BrowsingHistoryResponse;
import com.hepsiburada.api.mapper.BrowsingHistoryResponseMapper;
import com.hepsiburada.api.repository.ProductViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class BrowsingHistoryService {

    private final ProductViewRepository productViewRepository;

    public BrowsingHistoryResponse getLatestViewsOfUser(String userId) {
//        BrowsingHistoryResponse response = BrowsingHistoryResponse.builder()
//                .userId(userId)
//                .products(new ArrayList<>())
//                .build();
//        productViewRepository.findByUserIdOrderByCreatedDate(userId, PageRequest.of(0, 10))
//                .forEach(productView -> response.getProducts().add(productView.getProductId()));

        return BrowsingHistoryResponseMapper.mapPersonalizedRecommendations(userId,
                productViewRepository.findByUserIdOrderByCreatedDate(userId, PageRequest.of(0, 10)));
    }

    @Transactional
    public void deleteFromUserBrowsingHistory(String userId, String productId) {
        productViewRepository.deleteByUserIdAndProductId(userId, productId);
    }
}
