package com.flowshop.common.api.response;

import java.util.Map;

public record ApiErrorResponse(
        int status,
        String error,
        String message,
        String path,
        String timestamp,
        Map<String, String> fieldErrors
) {}
