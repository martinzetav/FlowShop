package com.microservice.order.dto;

public record ProductOrderDTO(Long productId,
                              Integer quantity,
                              Double price) {
}
