package com.hepsiburada.api.controller.response;

import com.hepsiburada.api.model.RecommendationType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@ApiModel(description = "Recommendation dto for responses")
public class RecommendationResponse {
    @ApiModelProperty(position = 1, value = "Id of the user entity", example = "user-102")
    private String userId;
    @ApiModelProperty(position = 2, value = "Products recommended to the user")
    private List<String> products;
    @ApiModelProperty(position = 3, value = "Type: Either personalized or non-personalized", example = "non-personalized")
    private RecommendationType recommendationType;

}
