package com.microservice.cart.dto.response;

import java.util.List;

public record CartResponseDTO(Long id,
                              Long userId,
                              List<CartItemResponseDTO> items) {
}
