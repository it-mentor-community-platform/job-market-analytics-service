package com.itmentorcommunityplatform.job_market_analytics_service.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record HhVacancySearchResponse(

        @JsonProperty("items")
        List<HhVacancyItemResponse> items,

        @JsonProperty("found")
        int found,

        @JsonProperty("pages")
        int pages,

        @JsonProperty("page")
        int page,

        @JsonProperty("per_page")
        int perPage
) {
}
