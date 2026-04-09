package com.itmentorcommunityplatform.job_market_analytics_service.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record HhSalaryResponse(

        @JsonProperty("from")
        BigDecimal from,

        @JsonProperty("to")
        BigDecimal to,

        @JsonProperty("currency")
        String currency,

        @JsonProperty("gross")
        Boolean gross
) {
}
