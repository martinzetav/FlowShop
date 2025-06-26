package com.microservice.product.api.response;

public record ApiSuccessResponse<T>(
        int status,
        String message,
        T data,
        String timestamp,
        String path
) {}
