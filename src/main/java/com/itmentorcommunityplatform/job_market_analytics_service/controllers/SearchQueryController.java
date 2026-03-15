package com.itmentorcommunityplatform.job_market_analytics_service.controllers;

import com.itmentorcommunityplatform.job_market_analytics_service.dto.SearchQueryRequest;
import com.itmentorcommunityplatform.job_market_analytics_service.dto.SearchQueryResponse;
import com.itmentorcommunityplatform.job_market_analytics_service.service.SearchQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/job-market-analytics")
@RequiredArgsConstructor
public class SearchQueryController {

    private final SearchQueryService searchQueryService;

    @PostMapping("/search-query")
    public ResponseEntity<SearchQueryResponse> searchQuery(@RequestBody @Valid SearchQueryRequest request) {
        SearchQueryResponse response = searchQueryService.save(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
