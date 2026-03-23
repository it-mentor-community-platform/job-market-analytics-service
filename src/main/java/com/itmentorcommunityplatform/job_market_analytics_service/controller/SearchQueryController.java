package com.itmentorcommunityplatform.job_market_analytics_service.controller;

import com.itmentorcommunityplatform.job_market_analytics_service.dto.SearchQueryRequest;
import com.itmentorcommunityplatform.job_market_analytics_service.dto.SearchQueryResponse;
import com.itmentorcommunityplatform.job_market_analytics_service.exception.AccessDeniedException;
import com.itmentorcommunityplatform.job_market_analytics_service.service.SearchQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-market-analytics")
@RequiredArgsConstructor
public class SearchQueryController {

    private final SearchQueryService searchQueryService;

    @PostMapping("/search-query")
    public ResponseEntity<SearchQueryResponse> searchQuery(
            @RequestHeader("X-User-Roles") List<String> roles,
            @RequestBody @Valid SearchQueryRequest request) {

        if (roles.stream().noneMatch("ADMIN"::equalsIgnoreCase)) {
            throw new AccessDeniedException("Access denied");
        }

        SearchQueryResponse response = searchQueryService.save(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/search-query/{id}")
    public ResponseEntity<Void> updateSearchQuery(
            @RequestHeader("X-User-Roles") List<String> roles,
            @RequestBody @Valid SearchQueryRequest request,
            @PathVariable("id") Long queryId) {

        if (roles.stream().noneMatch("ADMIN"::equalsIgnoreCase)) {
            throw new AccessDeniedException("Access denied");
        }

        searchQueryService.update(queryId, request);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/search-query")
    public ResponseEntity<List<SearchQueryResponse>> getSearchQueries(
            @RequestParam(required = false) Boolean isEnabled) {

        List<SearchQueryResponse> responses = searchQueryService.getSearchQueries(isEnabled);

        return ResponseEntity.ok(responses);
    }
}
