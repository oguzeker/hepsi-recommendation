package com.hepsiburada.api.mapper;

import com.hepsiburada.api.controller.response.BrowsingHistoryResponse;
import com.hepsiburada.api.entity.ProductView;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;

import java.util.ArrayList;

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
