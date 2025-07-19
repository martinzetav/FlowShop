package com.microservice.cart.service.impl;

import com.flowshop.common.exception.UserServiceUnavailableException;
import com.microservice.cart.client.IUserClient;
import com.microservice.cart.dto.UserDTO;
import com.microservice.cart.service.IUserService;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final IUserClient userClient;

    @Override
    @CircuitBreaker(name = "user", fallbackMethod = "fallbackFindUserById")
    @Retry(name = "user")
    public UserDTO findUserById(Long id) {
        return userClient.getUserById(id);
    }

    public UserDTO fallbackFindUserById(Long id, CallNotPermittedException e){
        throw new UserServiceUnavailableException(
                "User service is currently unavailable. Please try again later."
        );
    }
}
