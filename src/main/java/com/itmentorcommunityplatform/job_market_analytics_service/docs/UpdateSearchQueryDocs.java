package com.itmentorcommunityplatform.job_market_analytics_service.docs;


import com.itmentorcommunityplatform.job_market_analytics_service.dto.SearchQueryRequest;
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
        summary = "Update search query",
        description = "Updates an existing search query by id. Access is allowed only for users with ADMIN role."
)
@Parameter(
        name = "X-User-Roles",
        description = "User roles header. Must contain ADMIN to access this endpoint.",
        required = true,
        in = ParameterIn.HEADER,
        example = "ADMIN"
)
@Parameter(
        name = "id",
        description = "Search query identifier",
        required = true,
        in = ParameterIn.PATH,
        example = "1"
)
@RequestBody(
        required = true,
        description = "Updated search query data",
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = SearchQueryRequest.class),
                examples = {
                        @ExampleObject(
                                name = "Update search query request",
                                value = """
                                        {
                                          "title": "Python Developer",
                                          "query": "NAME:(\\"Python\\") NOT QA NOT AQA",
                                          "isEnabled": true
                                        }
                                        """
                        )
                }
        )
)
@ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Search query updated successfully"
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Invalid request body, invalid path variable or missing required header",
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
                                        name = "Invalid path variable",
                                        value = """
                                                {
                                                  "message": "Invalid request"
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
                responseCode = "404",
                description = "Search query not found",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponseDto.class),
                        examples = {
                                @ExampleObject(
                                        name = "Not found",
                                        value = """
                                                {
                                                  "message": "Search query not found"
                                                }
                                                """
                                )
                        }
                )
        ),
        @ApiResponse(
                responseCode = "409",
                description = "Search query conflict",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponseDto.class),
                        examples = {
                                @ExampleObject(
                                        name = "Duplicate query",
                                        value = """
                                                {
                                                  "message": "Key (title)=(Python Developer) already exists."
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
public @interface UpdateSearchQueryDocs {
}
