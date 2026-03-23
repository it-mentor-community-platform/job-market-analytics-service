package com.itmentorcommunityplatform.job_market_analytics_service.service;

import com.itmentorcommunityplatform.job_market_analytics_service.domain.SearchQuery;
import com.itmentorcommunityplatform.job_market_analytics_service.dto.SearchQueryRequest;
import com.itmentorcommunityplatform.job_market_analytics_service.dto.SearchQueryResponse;
import com.itmentorcommunityplatform.job_market_analytics_service.exception.SearchQueryNotFoundException;
import com.itmentorcommunityplatform.job_market_analytics_service.mapper.SearchQueryMapper;
import com.itmentorcommunityplatform.job_market_analytics_service.repository.SearchQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public List<SearchQueryResponse> getSearchQueries(Boolean isEnabled) {
        List<SearchQuery> queries = isEnabled == null
                ? searchQueryRepository.findAll()
                : searchQueryRepository.findByIsEnabled(isEnabled);

        return queries.stream()
                .map(searchQueryMapper::mapToSearchQueryResponse)
                .toList();
    }

    @Transactional
    public void update(Long queryId, SearchQueryRequest request) {
        SearchQuery existingSearchQuery = searchQueryRepository.findById(queryId)
                .orElseThrow(() -> new SearchQueryNotFoundException("Search query not found"));

        existingSearchQuery.setTitle(request.title());
        existingSearchQuery.setQuery(request.query());
        existingSearchQuery.setEnabled(request.isEnabled());

        searchQueryRepository.save(existingSearchQuery);
    }
}
