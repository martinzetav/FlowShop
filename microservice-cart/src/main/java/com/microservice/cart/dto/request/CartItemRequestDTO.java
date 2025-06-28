package com.microservice.cart.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CartItemRequestDTO(
        @NotNull(message = "Product ID must not be null")
        Long productId,
        @NotNull(message = "Quantity must not be null")
        @Min(value = 1, message = "Quantity must be at least 1")
        Integer quantity
) {}
