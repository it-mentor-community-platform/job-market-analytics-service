package com.itmentorcommunityplatform.job_market_analytics_service.repository;

import com.itmentorcommunityplatform.job_market_analytics_service.domain.SearchQuery;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface SearchQueryRepository extends ListCrudRepository<SearchQuery, Long> {

    List<SearchQuery> findByIsEnabled(boolean isEnabled);
}