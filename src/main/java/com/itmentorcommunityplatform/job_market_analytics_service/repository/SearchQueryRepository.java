package com.itmentorcommunityplatform.job_market_analytics_service.repository;

import com.itmentorcommunityplatform.job_market_analytics_service.domain.SearchQuery;
import org.springframework.data.repository.CrudRepository;

public interface SearchQueryRepository extends CrudRepository<SearchQuery, Long> {

}
