package com.microservice.checkout.dto.request;

import java.util.List;

public record CartRequestDTO(Long userId,
                             List<CartItemRequestDTO> items) {
}
