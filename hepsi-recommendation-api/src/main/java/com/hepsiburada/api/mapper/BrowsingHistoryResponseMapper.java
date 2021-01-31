package com.hepsiburada.api.mapper;

import com.hepsiburada.api.controller.response.BrowsingHistoryResponse;
import com.hepsiburada.api.controller.response.RecommendationResponse;
import com.hepsiburada.api.entity.NonPersonalizedRecommendation;
import com.hepsiburada.api.entity.PersonalizedRecommendation;
import com.hepsiburada.api.entity.ProductView;
import com.hepsiburada.api.model.RecommendationType;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class BrowsingHistoryResponseMapper {

    public BrowsingHistoryResponse mapPersonalizedRecommendations(
            String userId, Page<ProductView> productViewPage) {
        BrowsingHistoryResponse browsingHistoryResponse = BrowsingHistoryResponse.builder()
                .userId(userId)
                .products(new ArrayList<>())
                .build();

        productViewPage.forEach(productView -> browsingHistoryResponse.getProducts()
                .add(productView.getProductId()));

        return browsingHistoryResponse;
    }

}
