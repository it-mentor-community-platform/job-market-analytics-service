CREATE TABLE search_queries
(
    id         BIGSERIAL PRIMARY KEY,
    title      VARCHAR(255) NOT NULL,
    query      TEXT         UNIQUE NOT NULL,
    is_enabled BOOLEAN      NOT NULL
);

CREATE TABLE market_data_points
(
    id                BIGSERIAL PRIMARY KEY,
    search_query_id   BIGINT    NOT NULL,
    snapshot_date     DATE      NOT NULL,
    vacancy_count     INT       NOT NULL,
    avg_salary        NUMERIC   NOT NULL,
    median_salary_mid NUMERIC   NOT NULL,
    created_at        TIMESTAMP NOT NULL,

    CONSTRAINT fk_search_query
        FOREIGN KEY (search_query_id)
            REFERENCES search_queries (id)
            ON DELETE CASCADE
);

CREATE UNIQUE INDEX idx_market_data_points_unique
    ON market_data_points (search_query_id, snapshot_date);