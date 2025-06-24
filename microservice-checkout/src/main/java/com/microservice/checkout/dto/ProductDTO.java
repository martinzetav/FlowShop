package com.microservice.checkout.dto;

public record ProductDTO(Long id,
                         String name,
                         String brand,
                         String description,
                         Double price,
                         Integer stock) {
}
