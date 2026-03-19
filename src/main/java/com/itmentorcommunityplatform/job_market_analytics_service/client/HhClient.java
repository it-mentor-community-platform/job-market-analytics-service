package com.itmentorcommunityplatform.job_market_analytics_service.client;

import com.itmentorcommunityplatform.job_market_analytics_service.exception.ExternalServiceException;
import com.itmentorcommunityplatform.job_market_analytics_service.exception.RateLimitExceededException;
import com.itmentorcommunityplatform.job_market_analytics_service.metrics.ProfileMetrics;
import io.github.bucket4j.Bucket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class HhClient {

    private final RestClient restClient;
    private final ProfileMetrics profileMetrics;
    private final Bucket hhApiBucket;

    public String searchVacancies(String query) {
        if (!hhApiBucket.tryConsume(1)) {
            log.warn("HH API rate limit exceeded");
            throw new RateLimitExceededException("Слишком много запросов к внешнему сервису");
        }

        try {
            String response = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/vacancies")
                            .queryParam("text", query)
                            .build())
                    .retrieve()
                    .body(String.class);

            log.info("HH API response for query '{}': {}", query, response);
            return response;

        } catch (Exception e) {
            log.error("HH API request failed for query '{}'", query, e);
            profileMetrics.getRequestErrorCounter().increment();
            throw new ExternalServiceException("Не удалось получить данные от внешнего сервиса");
        }
    }
}
