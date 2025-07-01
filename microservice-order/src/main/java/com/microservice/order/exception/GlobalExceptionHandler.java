package com.microservice.order.exception;

import com.flowshop.common.api.response.ApiErrorResponse;
import com.flowshop.common.exception.InsufficientStockException;
import com.flowshop.common.exception.ResourceNotFoundException;
import com.flowshop.common.util.ResponseBuilder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ApiErrorResponse> processInsufficientStockException(InsufficientStockException e,
                                                                              HttpServletRequest request){
        ApiErrorResponse error = ResponseBuilder.buildErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                e.getMessage(),
                request
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> processResourceNotFoundException(ResourceNotFoundException e,
                                                                             HttpServletRequest request){
        ApiErrorResponse error = ResponseBuilder.buildErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                e.getMessage(),
                request
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

}
