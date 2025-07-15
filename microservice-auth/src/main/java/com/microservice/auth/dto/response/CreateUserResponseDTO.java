package com.microservice.auth.dto.response;

public record CreateUserResponseDTO(
        String email,
        String password,
        String role
) {}
