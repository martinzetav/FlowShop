package com.microservice.order.service.impl;

import com.microservice.order.client.IUserClient;
import com.microservice.order.dto.UserDTO;
import com.microservice.order.service.IUserService;
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
