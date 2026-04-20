package com.itmentorcommunityplatform.job_market_analytics_service.client;

import com.itmentorcommunityplatform.job_market_analytics_service.dto.response.HhVacancySearchResponse;
import com.itmentorcommunityplatform.job_market_analytics_service.exception.ExternalServiceException;
import com.itmentorcommunityplatform.job_market_analytics_service.metrics.ProfileMetrics;
import io.github.bucket4j.Bucket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class HhClient {
    private static final DateTimeFormatter HH_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXX");

    private final RestClient restClient;
    private final ProfileMetrics profileMetrics;
    private final Bucket hhApiBucket;

    public HhVacancySearchResponse searchVacancies(String query, OffsetDateTime dateFrom, OffsetDateTime dateTo, int page, int perPage) {
        waitForRateLimitPermit();
        String from = dateFrom.truncatedTo(ChronoUnit.SECONDS).format(HH_DATE_TIME_FORMATTER);
        String to = dateTo.truncatedTo(ChronoUnit.SECONDS).format(HH_DATE_TIME_FORMATTER);

        try {
            HhVacancySearchResponse response = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/vacancies")
                            .queryParam("text", "{text}")
                            .queryParam("date_from", "{dateFrom}")
                            .queryParam("date_to", "{dateTo}")
                            .queryParam("page", "{page}")
                            .queryParam("per_page", "{perPage}")
                            .build(Map.of(
                                    "text", query,
                                    "dateFrom", from,
                                    "dateTo", to,
                                    "page", page,
                                    "perPage", perPage
                            )))
                    .retrieve()
                    .body(HhVacancySearchResponse.class);
            if (response == null) {
                throw new ExternalServiceException("Empty response from HH API");
            }

            log.debug("HH API request completed. searchQueryText='{}', page={}, perPage={}", query, page, perPage);
            return response;

        } catch (HttpClientErrorException.BadRequest e) {
            log.error("HH API returned 400 Bad Request. searchQueryText='{}', from='{}', to='{}', body={}",
                    query, from, to, e.getResponseBodyAsString(), e);
            profileMetrics.getRequestErrorCounter().increment();
            throw new ExternalServiceException("Invalid request to HH API");
        } catch (RestClientException e) {
            log.error("HH API request failed. searchQueryText='{}', from='{}', to='{}'",
                    query, from, to, e);
            profileMetrics.getRequestErrorCounter().increment();
            throw new ExternalServiceException("Failed to fetch data from HH API");
        } catch (Exception e) {
            log.error("HH API request failed for searchQueryText '{}'", query, e);
            profileMetrics.getRequestErrorCounter().increment();
            throw new ExternalServiceException("Failed to fetch data from HH API");
        }
    }

    private void waitForRateLimitPermit() {
        try {
            hhApiBucket.asBlocking().consume(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ExternalServiceException("Interrupted while waiting for HH API rate limit permit");
        }
    }
}
