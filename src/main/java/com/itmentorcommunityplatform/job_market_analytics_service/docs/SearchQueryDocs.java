package com.itmentorcommunityplatform.job_market_analytics_service.docs;

import com.itmentorcommunityplatform.job_market_analytics_service.dto.SearchQueryRequest;
import com.itmentorcommunityplatform.job_market_analytics_service.dto.SearchQueryResponse;
import com.itmentorcommunityplatform.job_market_analytics_service.dto.response.ErrorResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)

@Operation(
        summary = "Create search query",
        description = "Creates a new search query. Access is allowed only for users with ADMIN role."
)
@RequestBody(
        required = true,
        description = "Search query data",
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = SearchQueryRequest.class),
                examples = {
                        @ExampleObject(
                                name = "Create search query request",
                                value = """
                                        {
                                          "title": "Java Developer",
                                          "query": "NAME:(\\"Java\\") NOT QA NOT AQA",
                                          "isEnabled": true
                                        }
                                        """
                        )
                }
        )
)
@ApiResponses({
        @ApiResponse(
                responseCode = "201",
                description = "Search query created successfully",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = SearchQueryResponse.class),
                        examples = {
                                @ExampleObject(
                                        name = "Created search query",
                                        value = """
                                                {
                                                  "id": 1,
                                                  "title": "Java Developer",
                                                  "query": "NAME:(\\"Java\\") NOT QA NOT AQA",
                                                  "isEnabled": true
                                                }
                                                """
                                )
                        }
                )
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Invalid request body or missing required header",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponseDto.class),
                        examples = {
                                @ExampleObject(
                                        name = "Validation error",
                                        value = """
                                                {
                                                  "message": "Title is required"
                                                }
                                                """
                                ),
                                @ExampleObject(
                                        name = "Missing header",
                                        value = """
                                                {
                                                  "message": "Bad request"
                                                }
                                                """
                                )
                        }
                )
        ),
        @ApiResponse(
                responseCode = "403",
                description = "Access denied",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponseDto.class),
                        examples = {
                                @ExampleObject(
                                        name = "Forbidden",
                                        value = """
                                                {
                                                  "message": "Access denied"
                                                }
                                                """
                                )
                        }
                )
        ),
        @ApiResponse(
                responseCode = "409",
                description = "Search query already exists",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponseDto.class),
                        examples = {
                                @ExampleObject(
                                        name = "Duplicate query",
                                        value = """
                                                {
                                                  "message": "Key (title)=(Java Developer) already exists."
                                                }
                                                """
                                )
                        }
                )
        ),
        @ApiResponse(
                responseCode = "500",
                description = "Internal server error",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponseDto.class),
                        examples = {
                                @ExampleObject(
                                        name = "Unexpected error",
                                        value = """
                                                {
                                                  "message": "Internal server error"
                                                }
                                                """
                                )
                        }
                )
        )
})
@Parameter(
        name = "X-User-Roles",
        description = "User roles header. Must contain ADMIN to access this endpoint.",
        required = true,
        in = ParameterIn.HEADER,
        example = "ADMIN"
)
public @interface SearchQueryDocs {
}
