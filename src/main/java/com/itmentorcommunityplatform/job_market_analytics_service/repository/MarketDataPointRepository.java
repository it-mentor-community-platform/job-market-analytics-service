package com.itmentorcommunityplatform.job_market_analytics_service.repository;

import com.itmentorcommunityplatform.job_market_analytics_service.domain.MarketDataPoint;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public interface MarketDataPointRepository extends CrudRepository<MarketDataPoint, Long> {

    @Modifying
    @Query("""
            INSERT INTO market_data_points (
                search_query_id,
                snapshot_date,
                vacancy_count,
                avg_salary,
                median_salary_mid,
                created_at
            )
            VALUES (
                :searchQueryId,
                :snapshotDate,
                :vacancyCount,
                :avgSalary,
                :medianSalaryMid,
                :createdAt
            )
            ON CONFLICT (search_query_id, snapshot_date) DO NOTHING
            """)
    int insertIgnoreRaw(
            @Param("searchQueryId") Long searchQueryId,
            @Param("snapshotDate") LocalDate snapshotDate,
            @Param("vacancyCount") Integer vacancyCount,
            @Param("avgSalary") BigDecimal avgSalary,
            @Param("medianSalaryMid") BigDecimal medianSalaryMid,
            @Param("createdAt") LocalTime createdAt
    );

    default int insertIfAbsent(MarketDataPoint point) {
        return insertIgnoreRaw(
                point.getSearchQueryId(),
                point.getSnapshotDate(),
                point.getVacancyCount(),
                point.getAvgSalary(),
                point.getMedianSalaryMid(),
                point.getCreatedAt()
        );

    }
}
