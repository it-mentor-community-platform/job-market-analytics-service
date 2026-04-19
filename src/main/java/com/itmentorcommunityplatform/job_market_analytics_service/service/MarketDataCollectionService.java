package com.itmentorcommunityplatform.job_market_analytics_service.service;

import com.itmentorcommunityplatform.job_market_analytics_service.client.HhClient;
import com.itmentorcommunityplatform.job_market_analytics_service.domain.MarketDataPoint;
import com.itmentorcommunityplatform.job_market_analytics_service.dto.HhMarketDataRequest;
import com.itmentorcommunityplatform.job_market_analytics_service.dto.response.HhVacancyItemResponse;
import com.itmentorcommunityplatform.job_market_analytics_service.dto.response.HhVacancySearchResponse;
import com.itmentorcommunityplatform.job_market_analytics_service.repository.MarketDataPointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MarketDataCollectionService {
    private static final int HH_RESULT_LIMIT = 2000;
    private static final int FIRST_PAGE = 0;
    private static final int PER_PAGE = 100;
    private static final int MIN_SPLIT_WINDOW = 5;

    private final MarketDataAggregationService marketDataAggregationService;
    private final MarketDataPointRepository marketDataPointRepository;
    private final HhClient hhClient;

    public int collectAndSaveMarketData(HhMarketDataRequest searchRequest) {
        List<HhVacancyItemResponse> vacanciesValue = new ArrayList<>(
                fetchVacancies(searchRequest,
                        searchRequest.dateFrom(),
                        searchRequest.dateTo())
                        .values());
        MarketDataPoint point = marketDataAggregationService
                .aggregateToMarketDataPoint(searchRequest.id(), searchRequest.snapshotDate(), vacanciesValue);

        return marketDataPointRepository.insertIfAbsent(point);
    }

    private Map<String, HhVacancyItemResponse> fetchVacancies(
            HhMarketDataRequest searchRequest,
            OffsetDateTime from,
            OffsetDateTime to
    ) {
        Map<String, HhVacancyItemResponse> uniqueVacanciesById = new HashMap<>();

        String searchText = searchRequest.searchQueryText();

        HhVacancySearchResponse firstPageResult = hhClient.searchVacancies(searchText, from, to, FIRST_PAGE, PER_PAGE);
        int totalFound = firstPageResult.found();

        if (totalFound <= HH_RESULT_LIMIT) {
            fetchAllPages(searchText, from, to, firstPageResult, uniqueVacanciesById);
        } else {
            if (!canSplitFurther(from, to)) {
                log.warn("HH response still exceeds 2000 results in minimal time window - data may be distorted. searchQueryId={}, from={}, to={}, totalFound={}",
                        searchRequest.id(), from, to, totalFound);
                fetchAllPages(searchText, from, to, firstPageResult, uniqueVacanciesById);
            }
            OffsetDateTime mid = midpoint(from, to);
            firstPageResult = hhClient.searchVacancies(searchText, from, mid, FIRST_PAGE, PER_PAGE);
            fetchAllPages(searchText, from, mid, firstPageResult, uniqueVacanciesById);

            firstPageResult = hhClient.searchVacancies(searchText, mid, to, FIRST_PAGE, PER_PAGE);
            fetchAllPages(searchText, mid, to, firstPageResult, uniqueVacanciesById);
        }
        return uniqueVacanciesById;
    }

    private void fetchAllPages(
            String searchText,
            OffsetDateTime from,
            OffsetDateTime to,
            HhVacancySearchResponse firstPageResult,
            Map<String, HhVacancyItemResponse> uniqueVacanciesById
    ) {
        addUniqueVacancies(firstPageResult.items(), uniqueVacanciesById);

        for (int page = 1; page < firstPageResult.pages(); page++) {
            HhVacancySearchResponse response = hhClient.searchVacancies(searchText, from, to, page, PER_PAGE);
            addUniqueVacancies(response.items(), uniqueVacanciesById);
        }
    }

    private void addUniqueVacancies(List<HhVacancyItemResponse> items, Map<String, HhVacancyItemResponse> uniqueVacanciesById) {
        if (items == null || items.isEmpty()) {
            return;
        }
        for (HhVacancyItemResponse item : items) {
            if (item != null && item.id() != null) {
                uniqueVacanciesById.putIfAbsent(item.id(), item);
            }
        }
    }

    private boolean canSplitFurther(OffsetDateTime from, OffsetDateTime to) {
        return Duration.between(from, to).toMinutes() > MIN_SPLIT_WINDOW;
    }

    private OffsetDateTime midpoint(OffsetDateTime from, OffsetDateTime to) {
        return from.plus(Duration.between(from, to).dividedBy(2));
    }
}
