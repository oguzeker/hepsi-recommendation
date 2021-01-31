package com.hepsiburada.api.controller.impl;

import com.hepsiburada.api.controller.BrowsingHistoryController;
import com.hepsiburada.api.controller.response.BrowsingHistoryResponse;
import com.hepsiburada.api.service.BrowsingHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/history")
public class BrowsingHistoryControllerImpl implements BrowsingHistoryController {

    private final BrowsingHistoryService browsingHistoryService;

    @GetMapping(value = "/userId/{userId}")
    public ResponseEntity<BrowsingHistoryResponse> getUserBrowsingHistory(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(browsingHistoryService.getLatestViewsOfUser(userId));
    }

    @DeleteMapping(value = "/userId/{userId}/productId/{productId}")
    public ResponseEntity deleteFromUserBrowsingHistory(@PathVariable("userId") String userId,
                                              @PathVariable("productId") String productId) {
        browsingHistoryService.deleteFromUserBrowsingHistory(userId, productId);
        return ResponseEntity.ok().build();
    }

}
