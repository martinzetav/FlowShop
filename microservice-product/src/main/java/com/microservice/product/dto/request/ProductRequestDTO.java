package com.microservice.product.dto.request;

import jakarta.validation.constraints.*;

public record ProductRequestDTO(
        @NotBlank(message = "The name field is required")
        @Size(max = 100, message = "The name field cannot exceed 100 characters")
        String name,

        @NotBlank(message = "The brand field is required")
        @Size(max = 50, message = "The brand field cannot exceed 50 characters")
        String brand,

        @NotBlank(message = "The description field is required")
        @Size(max = 200, message = "The description field cannot exceed 200 characters")
        String description,

        @NotNull(message = "The price field is required")
        @Positive(message = "The price field must be greater than zero")
        Double price,

        @NotNull(message = "The stock field is required")
        @Min(value = 0, message = "The stock field cannot be negative")
        Integer stock
) {}
