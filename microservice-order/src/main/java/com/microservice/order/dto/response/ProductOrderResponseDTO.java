package com.microservice.order.dto.response;

public record ProductOrderResponseDTO(Long productId,
                                      Integer quantity,
                                      Double price) {
}
