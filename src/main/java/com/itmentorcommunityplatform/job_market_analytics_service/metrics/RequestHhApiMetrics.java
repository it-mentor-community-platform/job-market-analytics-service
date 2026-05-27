package com.itmentorcommunityplatform.job_market_analytics_service.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class RequestHhApiMetrics {

    private final Counter requestErrorCounter;
    private final Counter requestSuccessCounter;

    public RequestHhApiMetrics(MeterRegistry meterRegistry) {
        this.requestErrorCounter = Counter.builder("hh_api_requests_total")
                .description("Total failed HH API requests")
                .tag("status", "error")
                .register(meterRegistry);

        this.requestSuccessCounter = Counter.builder("hh_api_requests_total")
                .description("Total success HH API requests")
                .tag("status", "success")
                .register(meterRegistry);

    }
}