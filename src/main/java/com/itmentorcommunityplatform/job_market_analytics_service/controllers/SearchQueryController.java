package com.itmentorcommunityplatform.job_market_analytics_service.controllers;

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
            @RequestHeader(value = "X-User-Roles") List<String> roles,
            @RequestHeader(value = "X-Telegram-User-id") Long telegramUserId,
            @RequestBody @Valid SearchQueryRequest request) {

        if (roles == null || roles.stream().noneMatch(r -> r.equalsIgnoreCase("ADMIN"))) {
            throw new AccessDeniedException("Access denied");
        }

        SearchQueryResponse response = searchQueryService.save(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
