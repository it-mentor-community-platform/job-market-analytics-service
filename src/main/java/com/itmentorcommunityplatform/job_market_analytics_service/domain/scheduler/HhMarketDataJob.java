package com.itmentorcommunityplatform.job_market_analytics_service.domain.scheduler;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public record HhMarketDataJob(
        Long id,
        Long searchQueryId,
        LocalDate snapshotDate,
        OffsetDateTime dateFrom,
        OffsetDateTime dateTo,
        String searchQueryText
) {
}