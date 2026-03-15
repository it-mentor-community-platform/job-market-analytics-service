package com.itmentorcommunityplatform.job_market_analytics_service.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@RequiredArgsConstructor
@Table("market_data_points")
public class MarketDataPoint {

    @Id
    private Long id;

    @Column("search_query_id")
    private Long searchQueryId;

    @Column("snapshot_date")
    private LocalDate snapshotDate;

    @Column("vacancy_count")
    private int vacancyCount;

    @Column("avg_salary")
    private BigDecimal avgSalary;

    @Column("median_salary_mid")
    private BigDecimal medianSalaryMid;

    @Column("created_at")
    private LocalTime createdAt;
}
