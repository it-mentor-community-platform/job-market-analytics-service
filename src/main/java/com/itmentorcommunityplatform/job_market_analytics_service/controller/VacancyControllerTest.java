package com.itmentorcommunityplatform.job_market_analytics_service.controller;

import com.itmentorcommunityplatform.job_market_analytics_service.dto.response.HhVacancySearchResponse;
import com.itmentorcommunityplatform.job_market_analytics_service.service.VacancyService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/job-marker-analytics")
@RequiredArgsConstructor
public class VacancyControllerTest {

    private final VacancyService vacancyService;

    @PostMapping("/test-search")
    @Hidden
    public ResponseEntity<HhVacancySearchResponse> vacancySearchTest(@RequestParam String search) {
        return ResponseEntity.ok(vacancyService.searchVacancies(search));
    }
}