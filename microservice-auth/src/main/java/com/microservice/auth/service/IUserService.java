package com.microservice.auth.service;

import com.microservice.auth.dto.response.UserResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {
    Page<UserResponseDTO> findAllUsers(Pageable pageable);
    UserResponseDTO findUserById(Long id);
}
