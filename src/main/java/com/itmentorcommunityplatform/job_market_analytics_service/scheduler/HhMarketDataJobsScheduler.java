package com.itmentorcommunityplatform.job_market_analytics_service.scheduler;

import com.itmentorcommunityplatform.job_market_analytics_service.repository.HhScheduledJobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "scheduler.job-creator.enabled", matchIfMissing = true)
public class HhMarketDataJobsScheduler {

    private final HhScheduledJobRepository hhScheduledJobRepository;

    @Value("${scheduler.zone}")
    private ZoneId hhApiZone;

    @Value("${hh.collection.lookback-days}")
    private int searchPeriodDays;

    @Scheduled(
            cron = "${scheduler.job-creator.cron}",
            zone = "${scheduler.zone}")
    public void scheduleMarketDataJobs() {
        OffsetDateTime now = OffsetDateTime.now(hhApiZone).truncatedTo(ChronoUnit.SECONDS);
        LocalDate snapshotDate = now.toLocalDate();
        OffsetDateTime searchDateFrom = now.minusDays(searchPeriodDays);
        int createdJobs = hhScheduledJobRepository.scheduleJobs(snapshotDate, searchDateFrom, now);

        if (createdJobs > 0) {
            log.info("Scheduled market data jobs. snapshotDate={}, createdJobs={}", snapshotDate, createdJobs);
        } else {
            log.info("Scheduler worked but no new tasks were scheduled");
        }
    }
}
