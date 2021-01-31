package com.hepsiburada.api.controller;

import com.hepsiburada.api.configuration.SwaggerConfiguration;
import com.hepsiburada.api.controller.response.BrowsingHistoryResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Api(tags = {
        SwaggerConfiguration.TAG_BROWSING_HISTORY
})
public interface BrowsingHistoryController {
    @ApiOperation(value = "Get Browsing History Of User", notes = "This endpoint fetches the last ten products viewed by a given user")
    ResponseEntity<BrowsingHistoryResponse> getUserBrowsingHistory(
            @ApiParam(value = "Id of the user entity to query for", example = "user-102") @PathVariable("userId") String userId);

    @ApiOperation(value = "Delete From Browsing History Of User", notes = "This endpoint deletes product view records from the browsing history a given user")
    ResponseEntity deleteFromUserBrowsingHistory(
            @ApiParam(value = "Id of the user entity to query for", example = "user-102") @PathVariable("userId") String userId,
            @ApiParam(value = "Id of the product to delete", example = "product-25") @PathVariable("productId") String productId);
}
