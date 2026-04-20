package com.itmentorcommunityplatform.job_market_analytics_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
    @Value("${hh.app-token}")
    String applicationAccessToken;

    @Value("${hh.email}")
    String email;

    @Bean
    public RestClient hhRestClient(@Value("${hh.api.url}") String baseUrl) {

        return RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("User-Agent", "job-market-analytics-service/1.0 (" + email + ")")
                .defaultHeader("Authorization", "Bearer " + applicationAccessToken)
                .build();
    }
}
