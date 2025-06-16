package com.microservice.checkout.dto;

public record CartItemDTO(Long id,
                          Long productId,
                          Integer quantity) {
}
