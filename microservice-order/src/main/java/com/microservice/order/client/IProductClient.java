package com.microservice.order.client;

import com.microservice.order.dto.ProductDTO;
import com.microservice.order.dto.StockUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "msvc-product")
public interface IProductClient {

    @GetMapping("/products/internal/{id}")
    ProductDTO getProductById(@PathVariable Long id);

    @PutMapping("/products/internal/{id}/stock")
    void subtractStock(@PathVariable Long id, @RequestBody StockUpdateRequest stockUpdateRequest);
}
