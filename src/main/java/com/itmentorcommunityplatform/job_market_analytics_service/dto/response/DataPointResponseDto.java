package com.itmentorcommunityplatform.job_market_analytics_service.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DataPointResponseDto(
        @JsonFormat(pattern = "dd.MM.yyyy")
        LocalDate date,
    int vacancyCount,
    BigDecimal averageSalary
) {
}
