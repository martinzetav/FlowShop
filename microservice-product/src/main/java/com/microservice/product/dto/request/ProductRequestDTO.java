package com.microservice.product.dto.request;

public record ProductRequestDTO(String name,
                                String brand,
                                String description,
                                Double price,
                                Integer stock) {
}
