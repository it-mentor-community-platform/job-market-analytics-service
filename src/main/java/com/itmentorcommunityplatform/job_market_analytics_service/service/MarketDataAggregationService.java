package com.itmentorcommunityplatform.job_market_analytics_service.service;

import com.itmentorcommunityplatform.job_market_analytics_service.domain.MarketDataPoint;
import com.itmentorcommunityplatform.job_market_analytics_service.dto.response.HhVacancyItemResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class MarketDataAggregationService {

    @Value("${scheduler.zone}")
    private ZoneId hhApiZone;

    public MarketDataPoint aggregateToMarketDataPoint(Long searchQueryId, LocalDate snapshotDate, List<HhVacancyItemResponse> vacancies) {
        MarketDataPoint marketDataPoint = new MarketDataPoint();
        marketDataPoint.setSearchQueryId(searchQueryId);
        marketDataPoint.setSnapshotDate(snapshotDate);
        marketDataPoint.setVacancyCount(vacancies.size());
        marketDataPoint.setCreatedAt(LocalTime.now(hhApiZone).truncatedTo(ChronoUnit.SECONDS));

        if (vacancies.isEmpty()) {
            marketDataPoint.setAvgSalary(BigDecimal.ZERO);
            marketDataPoint.setMedianSalaryMid(BigDecimal.ZERO);
            return marketDataPoint;
        }

        List<BigDecimal> salaries = new ArrayList<>();

        for (HhVacancyItemResponse item : vacancies) {
            BigDecimal normalizedSalary = extractNormalizedRubSalary(item);
            if (normalizedSalary != null) {
                salaries.add(normalizedSalary);
            }
        }

        marketDataPoint.setAvgSalary(calculateAverage(salaries));
        marketDataPoint.setMedianSalaryMid(calculateMedian(salaries));

        return marketDataPoint;
    }

    private BigDecimal extractNormalizedRubSalary(HhVacancyItemResponse item) {
        if (item == null || item.salary() == null) {
            return null;
        }

        var salary = item.salary();

        if (!"RUR".equals(salary.currency())) {
            return null;
        }

        BigDecimal from = salary.from();
        BigDecimal to = salary.to();

        if (from != null && to != null) {
            return from.add(to).divide(BigDecimal.valueOf(2), 0, RoundingMode.HALF_UP);
        }

        if (from != null) {
            return from;
        }

        return to;
    }

    private BigDecimal calculateAverage(List<BigDecimal> salaries) {
        if (salaries.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal sum = BigDecimal.ZERO;

        for (BigDecimal salary : salaries) {
            sum = sum.add(salary);
        }

        return sum.divide(BigDecimal.valueOf(salaries.size()), 0, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateMedian(List<BigDecimal> salaries) {
        if (salaries.isEmpty()) {
            return BigDecimal.ZERO;
        }

        List<BigDecimal> sorted = new ArrayList<>(salaries);
        sorted.sort(Comparator.naturalOrder());

        int size = sorted.size();
        int middle = size / 2;

        if (size % 2 == 1) {
            return sorted.get(middle);
        }

        BigDecimal left = sorted.get(middle - 1);
        BigDecimal right = sorted.get(middle);

        return left.add(right).divide(BigDecimal.valueOf(2), 0, RoundingMode.HALF_UP);
    }
}
