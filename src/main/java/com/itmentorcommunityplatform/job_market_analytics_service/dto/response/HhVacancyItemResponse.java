package com.itmentorcommunityplatform.job_market_analytics_service.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record HhVacancyItemResponse(

        @JsonProperty("id")
        String id,

        @JsonProperty("salary")
        HhSalaryResponse salary
) {
}
