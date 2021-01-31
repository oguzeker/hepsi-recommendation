package com.hepsiburada.api.controller;

import com.hepsiburada.api.configuration.SwaggerConfiguration;
import com.hepsiburada.api.controller.response.RecommendationResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Api(tags = {
        SwaggerConfiguration.TAG_RECOMMENDATION
})
public interface RecommendationController {
    @ApiOperation(value = "Get Recommended Products For User", notes = "This endpoint fetches recommended best-selling products to the given user, either personalized or non-personalized")
    ResponseEntity<RecommendationResponse> getRecommendation(
            @ApiParam(value = "Id of the user entity to query for", example = "user-102") @PathVariable("userId") String userId);
}
