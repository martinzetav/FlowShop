package com.microservice.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"email", "message", "jwt", "status"})
public record AuthResponseDTO(
        String email,
        String message,
        String jwt,
        boolean status
) {}
