package com.itmentorcommunityplatform.job_market_analytics_service.docs;

import com.itmentorcommunityplatform.job_market_analytics_service.dto.SearchQueryResponse;
import com.itmentorcommunityplatform.job_market_analytics_service.dto.response.ErrorResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)

@Operation(
        summary = "Get search queries",
        description = "Returns all search queries. If isEnabled is provided, returns only filtered search queries."
)
@Parameters({
        @Parameter(
                name = "isEnabled",
                description = "Filter by search query status",
                in = ParameterIn.QUERY,
                example = "true"
        )
})
@ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Search queries retrieved successfully",
                content = @Content(
                        mediaType = "application/json",
                        array = @ArraySchema(schema = @Schema(implementation = SearchQueryResponse.class))
                )
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Invalid request parameter",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponseDto.class),
                        examples = {
                                @ExampleObject(
                                        name = "Invalid request",
                                        value = """
                                                {
                                                    "message": "Invalid request"
                                                }
                                                """
                                )
                        }
                )
        )
})
public @interface GetSearchQueriesDocs {
}
