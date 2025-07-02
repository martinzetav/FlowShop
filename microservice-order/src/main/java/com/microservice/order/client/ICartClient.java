package com.microservice.order.client;

import com.microservice.order.dto.CartDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-cart")
public interface ICartClient {

    @GetMapping("/carts/internal/{id}")
    CartDTO getCartById(@PathVariable Long id);

    @PatchMapping("/carts/internal/{id}/complete")
    void completeCart(@PathVariable Long id);

}
