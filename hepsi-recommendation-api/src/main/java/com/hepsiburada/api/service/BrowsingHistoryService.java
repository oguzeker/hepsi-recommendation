package com.hepsiburada.api.service;

import com.hepsiburada.api.controller.response.BrowsingHistoryResponse;
import com.hepsiburada.api.mapper.BrowsingHistoryResponseMapper;
import com.hepsiburada.api.repository.ProductViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BrowsingHistoryService {

    private static final int PAGE_SIZE = 10;
    public static final int PAGE_INDEX_FIRST = 0;
    private final ProductViewRepository productViewRepository;

    public BrowsingHistoryResponse getLatestViewsOfUser(String userId) {
        return BrowsingHistoryResponseMapper.mapPersonalizedRecommendations(userId,
                productViewRepository.findByUserIdOrderByCreatedDate(userId,
                        PageRequest.of(PAGE_INDEX_FIRST, PAGE_SIZE)));
    }

    @Transactional
    public void deleteFromUserBrowsingHistory(String userId, String productId) {
        productViewRepository.deleteByUserIdAndProductId(userId, productId);
    }
}
