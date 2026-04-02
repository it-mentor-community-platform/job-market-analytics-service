package com.itmentorcommunityplatform.job_market_analytics_service.service;

import com.itmentorcommunityplatform.job_market_analytics_service.dto.response.DataPointResponseDto;
import com.itmentorcommunityplatform.job_market_analytics_service.mapper.DataPointMapper;
import com.itmentorcommunityplatform.job_market_analytics_service.repository.MarketDataPointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataPointService {

    private final MarketDataPointRepository marketDataPointRepository;
    private final DataPointMapper dataPointMapper;

    public List<DataPointResponseDto> getDataPoints(Long searchQueryId, LocalDate from, LocalDate to) {
        if (to == null) to = LocalDate.now();

        return marketDataPointRepository
                .findBySearchQueryIdAndSnapshotDateBetween(searchQueryId,from,to)
                .stream()
                .map(dataPointMapper::toDto)
                .toList();
    }
}
