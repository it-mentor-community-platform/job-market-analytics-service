package com.itmentorcommunityplatform.job_market_analytics_service.dto;

public record SearchQueryResponse(
        Long id,
        String title,
        String query,
        boolean isEnabled
) {
}
