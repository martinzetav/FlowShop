package com.microservice.cart.dto;

public record UserDTO(
        Long id,
        String username,
        String email,
        String roleName
) {}
