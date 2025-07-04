package com.microservice.cart.service.impl;

import com.flowshop.common.exception.ProductServiceUnavailableException;
import com.microservice.cart.client.IProductClient;
import com.microservice.cart.dto.ProductDTO;
import com.microservice.cart.service.IProductService;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final IProductClient productClient;


    @Override
    @CircuitBreaker(name = "product", fallbackMethod = "fallbackFindById")
    @Retry(name = "product")
    public ProductDTO findById(Long id) {
        return productClient.getProductById(id);
    }

    public ProductDTO fallbackFindById(Long id, CallNotPermittedException e){
        throw new ProductServiceUnavailableException(
                "Product service is currently unavailable. Please try again later."
        );
    }

}
