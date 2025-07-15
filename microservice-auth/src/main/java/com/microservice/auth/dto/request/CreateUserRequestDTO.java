package com.microservice.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateUserRequestDTO(
        @NotBlank(message = "The username is required.")
        String username,
        @NotBlank(message = "Email is required.")
        String email,
        @NotBlank(message = "Password is required.")
        String password
) {}
