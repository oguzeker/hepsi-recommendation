package com.hepsiburada.api.service;

import com.hepsiburada.api.controller.response.BrowsingHistoryResponse;

public interface BrowsingHistoryService {
    BrowsingHistoryResponse getLatestViewsOfUser(String userId);
    void deleteFromUserBrowsingHistory(String userId, String productId);
}
