package com.microservice.checkout.dto.request;

public record CartItemRequestDTO(Long productId,
                                 Integer quantity) {
}
