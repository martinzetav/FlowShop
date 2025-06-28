package com.microservice.cart.service.impl;

import com.microservice.cart.client.IProductClient;
import com.microservice.cart.dto.ProductDTO;
import com.microservice.cart.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final IProductClient productClient;

    @Override
    public ProductDTO findById(Long id) {
        return productClient.getProductById(id);
    }
}
