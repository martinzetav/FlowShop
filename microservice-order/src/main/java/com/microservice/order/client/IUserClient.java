package com.microservice.order.client;

import com.microservice.order.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-auth")
public interface IUserClient {

    @GetMapping("/users/internal/{id}")
    UserDTO getUserById(@PathVariable Long id);
}
