package com.itmentorcommunityplatform.job_market_analytics_service.dto;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public record HhMarketDataRequest(
        Long id,
        LocalDate snapshotDate,
        OffsetDateTime dateFrom,
        OffsetDateTime dateTo,
        String searchQueryText
) {
}
