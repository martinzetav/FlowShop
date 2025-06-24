package com.microservice.product.dto.response;

public record ProductResponseDTO(Long id,
                                 String name,
                                 String brand,
                                 String description,
                                 Double price,
                                 Integer stock) {
}
