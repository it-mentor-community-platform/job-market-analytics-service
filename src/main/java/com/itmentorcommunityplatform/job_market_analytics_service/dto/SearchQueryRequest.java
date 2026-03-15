package com.itmentorcommunityplatform.job_market_analytics_service.dto;

import jakarta.validation.constraints.NotNull;

public record SearchQueryRequest(
        @NotNull(message = "Title is required")
        String title,

        @NotNull(message = "Query is required")
        String query,

        @NotNull(message = "IsEnabled is required")
        Boolean isEnabled
) {
}
