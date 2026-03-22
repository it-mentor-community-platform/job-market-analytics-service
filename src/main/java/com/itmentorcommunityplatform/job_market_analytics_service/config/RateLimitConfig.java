package com.itmentorcommunityplatform.job_market_analytics_service.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RateLimitConfig {
    @Bean
    public Bucket hhApiBucket(
            @Value("${rate-limit.capacity}") int capacity,
            @Value("${rate-limit.refill}") int refill,
            @Value("${rate-limit.period}") Duration period
    ) {
        Bandwidth limit = Bandwidth.builder()
                .capacity(capacity)
                .refillGreedy(refill, period)
                .build();

        return Bucket.builder()
                .addLimit(limit)
                .build();
    }
}
