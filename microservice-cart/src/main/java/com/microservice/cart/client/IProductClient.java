package com.microservice.cart.client;

import com.microservice.cart.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-product")
public interface IProductClient {

    @GetMapping("/products/internal/{id}")
    ProductDTO getProductById(@PathVariable Long id);

}
