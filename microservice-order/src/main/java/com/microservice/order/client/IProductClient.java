package com.microservice.order.client;

import com.microservice.order.dto.ProductDTO;
import com.microservice.order.dto.StockUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "msvc-product")
public interface IProductClient {

    @GetMapping("/products/internal/{id}")
    ProductDTO getProductById(@PathVariable Long id);

    @PatchMapping("/products/internal/{id}/stock")
    void updateStock(@PathVariable Long id, @RequestBody StockUpdateRequest stockUpdateRequest);
}
