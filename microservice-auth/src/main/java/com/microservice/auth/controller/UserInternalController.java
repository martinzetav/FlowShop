package com.microservice.auth.controller;

import com.microservice.auth.dto.response.UserResponseDTO;
import com.microservice.auth.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/internal")
public class UserInternalController {

    private final IUserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.findUserById(id));
    }
}
