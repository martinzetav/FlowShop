package com.microservice.order.client;

import com.microservice.order.dto.CartDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "msvc-cart")
public interface ICartClient {

    @GetMapping("/carts/internal/{id}")
    CartDTO getCartById(@PathVariable Long id);

    @PutMapping("/carts/internal/{id}/complete")
    void completeCart(@PathVariable Long id);

}
