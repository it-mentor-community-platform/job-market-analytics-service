package com.itmentorcommunityplatform.job_market_analytics_service.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ProfileMetrics {

    private final Counter requestErrorCounter;

    public ProfileMetrics(MeterRegistry meterRegistry) {
        this.requestErrorCounter = Counter.builder("hh_api_requests_failed_total")
                .description("Total failed HH API requests")
                .register(meterRegistry);
    }
}
