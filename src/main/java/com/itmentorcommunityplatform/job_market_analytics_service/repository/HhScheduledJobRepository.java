package com.itmentorcommunityplatform.job_market_analytics_service.repository;

import com.itmentorcommunityplatform.job_market_analytics_service.domain.scheduler.HhMarketDataJob;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

public interface HhScheduledJobRepository extends CrudRepository<HhMarketDataJob, Long> {

    @Modifying
    @Query("""
            INSERT INTO hh_scheduled_jobs (search_query_id, snapshot_date, date_from, date_to, status)
            SELECT id,
                   :snapshotDate,
                   :dateFrom,
                   :dateTo,
                   'NEW'
            FROM search_queries
            WHERE is_enabled = true
            ON CONFLICT (search_query_id, snapshot_date) DO NOTHING
            """)
    int scheduleJobs(@Param("snapshotDate") LocalDate snapshotDate,
                     @Param("dateFrom") OffsetDateTime dateFrom,
                     @Param("dateTo") OffsetDateTime dateTo);

    @Query("""
                SELECT
                    j.id               AS id,
                    j.search_query_id  AS search_query_id,
                    j.snapshot_date    AS snapshot_date,
                    j.date_from        AS date_from,
                    j.date_to          AS date_to,
                    q.query            AS search_query_text
                FROM hh_scheduled_jobs j
                JOIN search_queries q ON q.id = j.search_query_id
                WHERE j.status = 'NEW'
                ORDER BY j.snapshot_date, j.search_query_id
            """)
    List<HhMarketDataJob> findNewJobs();

    @Modifying
    @Query(value = """
            UPDATE hh_scheduled_jobs
            SET status = 'DONE'
            WHERE id = :jobId
              AND status = 'NEW'
            """)
    void markJobDone(@Param("jobId") Long jobId);

}