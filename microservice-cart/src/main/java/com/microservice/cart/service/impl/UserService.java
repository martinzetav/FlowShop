package com.microservice.cart.service.impl;

import com.microservice.cart.client.IUserClient;
import com.microservice.cart.dto.UserDTO;
import com.microservice.cart.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final IUserClient userClient;

    @Override
    public UserDTO findUserById(Long id) {
        return userClient.getUserById(id);
    }
}
