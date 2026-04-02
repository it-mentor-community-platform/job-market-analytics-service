package com.itmentorcommunityplatform.job_market_analytics_service.mapper;

import com.itmentorcommunityplatform.job_market_analytics_service.domain.MarketDataPoint;
import com.itmentorcommunityplatform.job_market_analytics_service.dto.response.DataPointResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DataPointMapper {
    @Mapping(target = "date", expression = "java(point.getSnapshotDate().format(java.time.format.DateTimeFormatter.ofPattern(\"dd.MM.yyyy\")))")
    @Mapping(target = "vacancyCount", source = "vacancyCount")
    @Mapping(target = "averageSalary", source = "avgSalary")
    DataPointResponseDto toDto(MarketDataPoint point);
}
