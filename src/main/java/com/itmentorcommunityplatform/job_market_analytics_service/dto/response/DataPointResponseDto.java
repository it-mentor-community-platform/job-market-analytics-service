package com.itmentorcommunityplatform.job_market_analytics_service.dto.response;

import java.math.BigDecimal;

public record DataPointResponseDto(
    String date,
    int vacancyCount,
    BigDecimal averageSalary
) {
}
