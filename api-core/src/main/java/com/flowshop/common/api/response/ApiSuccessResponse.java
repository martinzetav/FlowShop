package com.flowshop.common.api.response;

public record ApiSuccessResponse<T>(
        int status,
        String message,
        T data,
        String timestamp,
        String path
) {}
