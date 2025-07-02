package com.microservice.cart.dto.response;

import com.microservice.cart.model.CartStatus;

import java.util.List;

public record CartResponseDTO(Long id,
                              Long userId,
                              CartStatus status,
                              List<CartItemResponseDTO> items) {
}
