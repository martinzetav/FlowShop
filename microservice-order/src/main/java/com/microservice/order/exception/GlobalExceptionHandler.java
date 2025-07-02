package com.microservice.order.exception;

import com.flowshop.common.api.response.ApiErrorResponse;
import com.flowshop.common.exception.InsufficientStockException;
import com.flowshop.common.exception.InvalidStockOperationException;
import com.flowshop.common.exception.ResourceNotFoundException;
import com.flowshop.common.util.ResponseBuilder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

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

    @ExceptionHandler(InvalidStockOperationException.class)
    public ResponseEntity<ApiErrorResponse> processInvalidStockOperationException(InvalidStockOperationException e,
                                                                                  HttpServletRequest request){
        ApiErrorResponse error = ResponseBuilder.buildErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                e.getMessage(),
                request
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> processMethodArgumentNotValidException(MethodArgumentNotValidException e,
                                                                                   HttpServletRequest request){
        Map<String, String> fieldErrors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        fieldError -> fieldError.getField(),
                        fieldError -> fieldError.getDefaultMessage(),
                        (existing, replacement) -> existing // en caso de campos duplicados
                ));


        ApiErrorResponse errorResponse = ResponseBuilder.buildValidationErrorResponse(request, fieldErrors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

    }

}
