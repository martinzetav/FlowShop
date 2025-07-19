package com.microservice.cart.client;

import com.microservice.cart.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-auth")
public interface IUserClient {

    @GetMapping("/users/internal/{id}")
    UserDTO getUserById(@PathVariable Long id);
}
