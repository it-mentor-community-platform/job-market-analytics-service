package com.itmentorcommunityplatform.job_market_analytics_service.mapper;

import com.itmentorcommunityplatform.job_market_analytics_service.domain.scheduler.HhMarketDataJob;
import com.itmentorcommunityplatform.job_market_analytics_service.dto.HhMarketDataRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ScheduledJobMapper {

    @Mapping(target = "id", source = "searchQueryId")
    HhMarketDataRequest toHhMarketDataRequest(HhMarketDataJob hhMarketDataJob);
}
