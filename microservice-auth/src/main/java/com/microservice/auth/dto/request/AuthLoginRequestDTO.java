package com.microservice.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequestDTO(
        @NotBlank(message = "Email is required.")
        String email,
        @NotBlank(message = "Password is required.")
        String password
) {}
