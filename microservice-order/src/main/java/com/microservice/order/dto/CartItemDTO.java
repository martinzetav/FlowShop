package com.microservice.order.dto;

public record CartItemDTO(Long id,
                          Long productId,
                          Integer quantity) {
}
