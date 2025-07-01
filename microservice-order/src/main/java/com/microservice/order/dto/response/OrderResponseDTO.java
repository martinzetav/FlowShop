package com.microservice.order.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDTO(Long id,
                               Long userId,
                               LocalDateTime date,
                               String status,
                               List<ProductOrderResponseDTO> orders,
                               Double totalPrice) {
}
