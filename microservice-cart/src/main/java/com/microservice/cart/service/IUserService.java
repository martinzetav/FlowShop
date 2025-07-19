package com.microservice.cart.service;

import com.microservice.cart.dto.UserDTO;

public interface IUserService {
    UserDTO findUserById(Long id);
}
