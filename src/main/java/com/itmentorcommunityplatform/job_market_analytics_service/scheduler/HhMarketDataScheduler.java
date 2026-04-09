package com.itmentorcommunityplatform.job_market_analytics_service.scheduler;

import com.itmentorcommunityplatform.job_market_analytics_service.domain.scheduler.HhMarketDataJob;
import com.itmentorcommunityplatform.job_market_analytics_service.dto.HhMarketDataRequest;
import com.itmentorcommunityplatform.job_market_analytics_service.mapper.ScheduledJobMapper;
import com.itmentorcommunityplatform.job_market_analytics_service.repository.HhScheduledJobRepository;
import com.itmentorcommunityplatform.job_market_analytics_service.service.MarketDataCollectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class HhMarketDataScheduler {

    private final MarketDataCollectionService marketDataCollectionService;
    private final HhScheduledJobRepository hhScheduledJobRepository;
    private final ScheduledJobMapper scheduledJobMapper;

    @Value("${scheduler.zone}")
    private ZoneId hhApiZone;

    @Value("${hh.collection.lookback-days}")
    private int searchPeriodDays;

    @Scheduled(
            cron = "${scheduler.cron}",
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

    @Scheduled(
            cron = "${scheduler.worker.cron}",
            zone = "${scheduler.zone}")
    public void collectHhMarketData() {
        List<HhMarketDataJob> jobs = hhScheduledJobRepository.findNewJobs();
        log.info("Market data collection started. jobsToProcess={}", jobs.size());

        int done = 0;
        int failed = 0;
        int skipped = 0;

        for (HhMarketDataJob job : jobs) {
            HhMarketDataRequest request = scheduledJobMapper.toHhMarketDataRequest(job);

            try {
                int completedJob = marketDataCollectionService.collectAndSaveMarketData(request);
                done += completedJob;

                if (completedJob == 0) {
                    skipped++;
                    log.info("Skipped market data insert because snapshot already exists. jobId={}, searchQueryId={}, snapshotDate={}",
                            job.id(), job.searchQueryId(), job.snapshotDate());
                    continue;
                }

                hhScheduledJobRepository.markJobDone(job.id());
            } catch (Exception e) {
                failed++;
                log.error("Market data job failed. jobId={}, searchQueryId={}, snapshotDate={}",
                        job.id(), job.searchQueryId(), job.snapshotDate(), e);
            }
        }

        log.info("Scheduler worker finished. tasks={}, done={}, failed={}, skipped={}", jobs.size(), done, failed, skipped);
    }
}
