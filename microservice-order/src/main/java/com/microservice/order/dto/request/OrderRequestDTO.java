package com.microservice.order.dto.request;

import java.util.List;

public record OrderRequestDTO(Long userId,
                              List<ProductOrderRequestDTO> orders) {
}
