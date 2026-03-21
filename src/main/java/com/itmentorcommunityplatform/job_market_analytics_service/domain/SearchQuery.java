package com.itmentorcommunityplatform.job_market_analytics_service.domain;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.HashSet;
import java.util.Set;

@Data
@Table("search_queries")
public class SearchQuery {

    @Id
    private Long id;

    @Column("title")
    @NonNull
    private String title;

    @Column("query")
    private String query;

    @Column("is_enabled")
    private boolean isEnabled;

    @MappedCollection(idColumn = "search_query_id")
    private Set<MarketDataPoint> marketDataPoints = new HashSet<>();
}
