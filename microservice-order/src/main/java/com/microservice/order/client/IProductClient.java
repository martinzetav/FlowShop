package com.microservice.order.client;

import com.microservice.order.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-product")
public interface IProductClient {

    @GetMapping("/products/{id}")
    ProductDTO getProductById(@PathVariable Long id);
}
