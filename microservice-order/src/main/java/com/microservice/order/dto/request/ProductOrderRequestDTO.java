package com.microservice.order.dto.request;

public record ProductOrderRequestDTO(Long productId,
                                     Integer quantity) {
}
