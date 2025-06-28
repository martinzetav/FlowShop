package com.microservice.cart.dto;

public record ProductDTO(Long id,
                         String name,
                         String brand,
                         String description,
                         Double price,
                         Integer stock) {
}
