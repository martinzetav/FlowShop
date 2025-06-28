package com.microservice.cart.dto.response;

public record CartItemResponseDTO(Long id,
                                  Long productId,
                                  Integer quantity) {
}
