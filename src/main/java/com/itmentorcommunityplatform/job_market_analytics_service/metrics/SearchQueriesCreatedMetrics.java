package com.itmentorcommunityplatform.job_market_analytics_service.metrics;

import com.itmentorcommunityplatform.job_market_analytics_service.repository.SearchQueryRepository;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class SearchQueriesCreatedMetrics {

    public SearchQueriesCreatedMetrics(
            MeterRegistry registry,
            SearchQueryRepository repository
    ) {

        Gauge.builder(
                        "search_queries_created_count",
                        repository,
                        SearchQueryRepository::count
                )
                .description("Current count of search queries in DB")
                .register(registry);
    }
}
