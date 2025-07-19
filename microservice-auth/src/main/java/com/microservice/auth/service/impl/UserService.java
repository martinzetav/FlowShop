package com.microservice.auth.service.impl;

import com.flowshop.common.exception.ResourceNotFoundException;
import com.microservice.auth.dto.response.UserResponseDTO;
import com.microservice.auth.mapper.UserMapper;
import com.microservice.auth.model.UserEntity;
import com.microservice.auth.repository.IUserRepository;
import com.microservice.auth.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Page<UserResponseDTO> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toResponseDto);
    }

    @Override
    public UserResponseDTO findUserById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found."));

        return userMapper.toResponseDto(user);
    }
}
