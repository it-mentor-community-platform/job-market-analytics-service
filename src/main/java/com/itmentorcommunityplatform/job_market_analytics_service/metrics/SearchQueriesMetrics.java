package com.itmentorcommunityplatform.job_market_analytics_service.metrics;


import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class SearchQueriesMetrics {

    private final Counter searchQueriesRequestsCounter;

    public SearchQueriesMetrics(MeterRegistry meterRegistry) {
        this.searchQueriesRequestsCounter = Counter.builder("search_queries_requests")
                .description("Total requests fetch search queries list")
                .register(meterRegistry);
    }
}
