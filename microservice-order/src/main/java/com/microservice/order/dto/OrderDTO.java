package com.microservice.order.dto;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDTO(Long id,
                       Long userId,
                       LocalDateTime date,
                       String status,
                       List<ProductOrderDTO> orders,
                       Double totalPrice) {
}
