package com.microservice.auth.dto.response;

public record UserResponseDTO(
        Long id,
        String username,
        String email,
        String roleName
) {
}
