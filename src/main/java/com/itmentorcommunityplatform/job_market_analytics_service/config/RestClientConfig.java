package com.itmentorcommunityplatform.job_market_analytics_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient hhRestClient(@Value("${hh.api.url}") String baseUrl) {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(baseUrl);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

        return RestClient.builder()
                .baseUrl(baseUrl)
                .uriBuilderFactory(factory)
                .build();
    }
}
