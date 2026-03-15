package com.itmentorcommunityplatform.job_market_analytics_service.service;

import com.itmentorcommunityplatform.job_market_analytics_service.domain.SearchQuery;
import com.itmentorcommunityplatform.job_market_analytics_service.dto.SearchQueryRequest;
import com.itmentorcommunityplatform.job_market_analytics_service.dto.SearchQueryResponse;
import com.itmentorcommunityplatform.job_market_analytics_service.mapper.SearchQueryMapper;
import com.itmentorcommunityplatform.job_market_analytics_service.repository.SearchQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchQueryService {

    private final SearchQueryRepository searchQueryRepository;
    private final SearchQueryMapper searchQueryMapper;

    public SearchQueryResponse save(SearchQueryRequest request) {
        SearchQuery searchQuery = searchQueryMapper.mapToSearchQuery(request);
        SearchQuery saved = searchQueryRepository.save(searchQuery);

        return searchQueryMapper.mapToSearchQueryResponse(saved);
    }
}
