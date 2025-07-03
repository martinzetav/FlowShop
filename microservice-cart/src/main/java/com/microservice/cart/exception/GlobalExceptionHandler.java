package com.microservice.cart.exception;

import com.flowshop.common.api.response.ApiErrorResponse;
import com.flowshop.common.exception.InsufficientStockException;
import com.flowshop.common.exception.ResourceNotFoundException;
import com.flowshop.common.util.ResponseBuilder;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

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

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> processResourceAlreadyExistsException(ResourceAlreadyExistsException e,
                                                                                  HttpServletRequest request){
        ApiErrorResponse error = ResponseBuilder.buildErrorResponse(
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                e.getMessage(),
                request
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ApiErrorResponse> processInsufficientStockException(InsufficientStockException e,
                                                                              HttpServletRequest request){
        ApiErrorResponse error = ResponseBuilder.buildErrorResponse(
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                e.getMessage(),
                request
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
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

    @ExceptionHandler(UserAlreadyHasActiveCartException.class)
    public ResponseEntity<ApiErrorResponse> processUserAlreadyHasActiveCartException(UserAlreadyHasActiveCartException e,
                                                                                     HttpServletRequest request){
        ApiErrorResponse error = ResponseBuilder.buildErrorResponse(
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                e.getMessage(),
                request
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(ProductServiceUnavailableException.class)
    public ResponseEntity<ApiErrorResponse> processProductServiceUnavailableException(ProductServiceUnavailableException e,
                                                                                     HttpServletRequest request){
        ApiErrorResponse error = ResponseBuilder.buildErrorResponse(
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase(),
                e.getMessage(),
                request
        );
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<?> handleFeign(FeignException ex) {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of(
                        "status", 503,
                        "message", "Servicio temporalmente no disponible",
                        "timestamp", LocalDateTime.now()
                ));
    }
}
