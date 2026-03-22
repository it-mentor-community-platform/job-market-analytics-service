package com.itmentorcommunityplatform.job_market_analytics_service.controller;

import com.itmentorcommunityplatform.job_market_analytics_service.service.VacancyService;
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
    public ResponseEntity<String> vacancySearchTest(@RequestParam String search) {
        return ResponseEntity.ok(vacancyService.searchVacancies(search));
    } 
}
