package com.microservice.checkout.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CartRequestDTO(
        @NotNull(message = "User ID must not be null")
        Long userId,
        @NotEmpty(message = "Cart must have at least one item")
        @Valid
        List<CartItemRequestDTO> items
) {}
