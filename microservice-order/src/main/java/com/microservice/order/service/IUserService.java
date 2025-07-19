package com.microservice.order.service;

import com.microservice.order.dto.UserDTO;

public interface IUserService {
    UserDTO findUserById(Long id);
}
