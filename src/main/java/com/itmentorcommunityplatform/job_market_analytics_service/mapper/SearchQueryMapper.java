package com.itmentorcommunityplatform.job_market_analytics_service.mapper;

import com.itmentorcommunityplatform.job_market_analytics_service.domain.SearchQuery;
import com.itmentorcommunityplatform.job_market_analytics_service.dto.SearchQueryRequest;
import com.itmentorcommunityplatform.job_market_analytics_service.dto.SearchQueryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SearchQueryMapper {

    @Mapping(target = "enabled", source = "isEnabled")
    SearchQuery toSearchQuery(SearchQueryRequest searchQueryRequest);

    @Mapping(target = "isEnabled", source = "enabled")
    SearchQueryResponse toSearchQueryResponse(SearchQuery searchQuery);
}
