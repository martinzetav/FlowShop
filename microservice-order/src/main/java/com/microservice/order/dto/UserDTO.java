package com.microservice.order.dto;

public record UserDTO(
        Long id,
        String username,
        String email,
        String roleName
) {}
