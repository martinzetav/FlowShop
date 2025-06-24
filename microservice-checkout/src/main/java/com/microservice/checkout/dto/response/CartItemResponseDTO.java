package com.microservice.checkout.dto.response;

public record CartItemResponseDTO(Long id,
                                  Long productId,
                                  Integer quantity) {
}
