package com.flowshop.common.util;

import com.flowshop.common.api.response.ApiErrorResponse;
import com.flowshop.common.api.response.ApiSuccessResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class ResponseBuilder {

    public static <T> ApiSuccessResponse<T> buildSuccessResponse(int status,
                                                                 String message,
                                                                 T data,
                                                                 HttpServletRequest request)
    {
        return new ApiSuccessResponse<>(
                status,
                message,
                data,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                request.getRequestURI()
        );
    }

    public static ApiErrorResponse buildErrorResponse(int status,
                                                      String error,
                                                      String message,
                                                      HttpServletRequest request
    ) {
        return new ApiErrorResponse(
                status,
                error,
                message,
                request.getRequestURI(),
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                null
        );
    }

    public static ApiErrorResponse buildValidationErrorResponse(
            HttpServletRequest request,
            Map<String, String> fieldErrors
    ) {
        return new ApiErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Validation errors in form fields",
                request.getRequestURI(),
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                fieldErrors
        );
    }


}
