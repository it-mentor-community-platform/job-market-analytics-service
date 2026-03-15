package com.itmentorcommunityplatform.job_market_analytics_service.exception;

import com.itmentorcommunityplatform.job_market_analytics_service.dto.response.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception e) {
        log.error("Unexpected error occurred.", e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDto("Internal server error"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto(e.getBindingResult().getFieldError().getDefaultMessage()));
    }

    @ExceptionHandler(ExternalServiceException.class)
    public ResponseEntity<ErrorResponseDto> handleExternalServiceException(ExternalServiceException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(new ErrorResponseDto(ex.getMessage()));
    }

    @ExceptionHandler(RateLimitExceededException.class)
    public ResponseEntity<ErrorResponseDto> handleRateLimitExceededException(RateLimitExceededException ex) {
        return ResponseEntity
                .status(HttpStatus.TOO_MANY_REQUESTS)
                .body(new ErrorResponseDto(ex.getMessage()));
    }
}
