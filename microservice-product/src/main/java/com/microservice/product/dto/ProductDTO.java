package com.microservice.product.dto;

public record ProductDTO(Long id,
                         String name,
                         String brand,
                         String description,
                         Double price) {
}
