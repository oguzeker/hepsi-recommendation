package com.hepsiburada.api.controller.response;

import com.hepsiburada.api.model.RecommendationType;
import lombok.*;

import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class RecommendationResponse {
    private String userId;
    private List<String> products;
    private RecommendationType recommendationType;

}
