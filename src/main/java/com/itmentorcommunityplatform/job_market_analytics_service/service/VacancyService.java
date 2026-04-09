package com.itmentorcommunityplatform.job_market_analytics_service.service;

import com.itmentorcommunityplatform.job_market_analytics_service.client.HhClient;
import com.itmentorcommunityplatform.job_market_analytics_service.dto.response.HhVacancySearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class VacancyService {

    @Value("${hh.collection.lookback-days}")
    private int searchPeriodDays;

    @Value("${scheduler.zone}")
    private ZoneId hhApiZone;

    private final HhClient hhClient;

    public HhVacancySearchResponse searchVacancies(String query) {
        OffsetDateTime to = OffsetDateTime.now(hhApiZone);
        OffsetDateTime from = to.minusDays(searchPeriodDays);
        int defaultPage = 0;
        int perPage = 100;
        return hhClient.searchVacancies(query, from, to, defaultPage, perPage);
    }
}
