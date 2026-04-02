package com.itmentorcommunityplatform.job_market_analytics_service.repository;


import com.itmentorcommunityplatform.job_market_analytics_service.domain.MarketDataPoint;
import org.springframework.data.repository.ListCrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface MarketDataPointRepository extends ListCrudRepository<MarketDataPoint, Long> {

    List<MarketDataPoint> findBySearchQueryIdAndSnapshotDateBetween(
            Long searchQueryId, LocalDate from, LocalDate to);
}
