package com.microservice.auth.controller;

import com.microservice.auth.dto.request.AuthLoginRequestDTO;
import com.microservice.auth.dto.request.CreateUserRequestDTO;
import com.microservice.auth.dto.response.AuthResponseDTO;
import com.microservice.auth.dto.response.CreateUserResponseDTO;
import com.microservice.auth.security.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<CreateUserResponseDTO> register(@RequestBody @Valid CreateUserRequestDTO createUserDTO) {
        return ResponseEntity.ok(authService.signUpUser(createUserDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid AuthLoginRequestDTO authLoginRequestDTO){
        return ResponseEntity.ok(authService.loginUser(authLoginRequestDTO));
    }
}
