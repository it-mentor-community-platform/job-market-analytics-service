package com.itmentorcommunityplatform.job_market_analytics_service.controller;

import com.itmentorcommunityplatform.job_market_analytics_service.dto.response.DataPointResponseDto;
import com.itmentorcommunityplatform.job_market_analytics_service.service.DataPointService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/job-market-analytics")
@RequiredArgsConstructor
public class DataPointController {

    private final DataPointService dataPointService;

    @GetMapping("/data-points")
    public ResponseEntity<List<DataPointResponseDto>> getDataPoints(
            @RequestParam Long searchQueryId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to){

        if(to == null) to = LocalDate.now();
        List<DataPointResponseDto> response = dataPointService.getDataPoints(searchQueryId, from, to);
        return ResponseEntity.ok(response);
    }
}
