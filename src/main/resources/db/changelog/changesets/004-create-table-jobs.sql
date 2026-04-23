CREATE TABLE hh_scheduled_jobs
(
    id              BIGSERIAL PRIMARY KEY,
    search_query_id BIGINT      NOT NULL,
    snapshot_date   DATE        NOT NULL,
    date_from       TIMESTAMPTZ NOT NULL,
    date_to         TIMESTAMPTZ NOT NULL,
    status          TEXT        NOT NULL,
    CHECK (status IN ('NEW', 'DONE')),
    CONSTRAINT uk_hh_collection_job UNIQUE (search_query_id, snapshot_date)
);