package com.hepsiburada.api.controller.response;

import lombok.*;

import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class BrowsingHistoryResponse {
    private String userId;
    private List<String> products;
}
