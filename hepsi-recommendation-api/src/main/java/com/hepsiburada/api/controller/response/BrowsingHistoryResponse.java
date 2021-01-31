package com.hepsiburada.api.controller.response;

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
@ApiModel(description = "Browsing history dto for responses")
public class BrowsingHistoryResponse {
    @ApiModelProperty(position = 1, value = "Id of the user entity", example = "user-102")
    private String userId;
    @ApiModelProperty(position = 2, value = "Previously viewed products by the user")
    private List<String> products;
}
