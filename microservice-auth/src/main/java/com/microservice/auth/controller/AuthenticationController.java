package com.microservice.auth.controller;

import com.microservice.auth.dto.request.AuthLoginRequestDTO;
import com.microservice.auth.dto.request.CreateUserRequestDTO;
import com.microservice.auth.dto.response.AuthResponseDTO;
import com.microservice.auth.dto.response.CreateUserResponseDTO;
import com.microservice.auth.security.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "User registration", description = "Creates a new user with the USER role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful registration"),
            @ApiResponse(responseCode = "400", description = "Invalid or missing data"),
            @ApiResponse(responseCode = "409", description = "Email already registered")
    })
    @PostMapping("/signup")
    public ResponseEntity<CreateUserResponseDTO> register(@RequestBody @Valid CreateUserRequestDTO createUserDTO) {
        return ResponseEntity.ok(authService.signUpUser(createUserDTO));
    }

    @Operation(summary = "User login", description = "Authenticates the user and returns a JWT token upon success.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful login"),
            @ApiResponse(responseCode = "400", description = "Email or password not provided correctly"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid AuthLoginRequestDTO authLoginRequestDTO){
        return ResponseEntity.ok(authService.loginUser(authLoginRequestDTO));
    }
}
