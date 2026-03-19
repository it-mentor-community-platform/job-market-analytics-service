package com.itmentorcommunityplatform.job_market_analytics_service.service;

import com.itmentorcommunityplatform.job_market_analytics_service.client.HhClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VacancyService {

    private final HhClient hhClient;

    public String searchVacancies(String query) {
        return hhClient.searchVacancies(query);
    }
}
