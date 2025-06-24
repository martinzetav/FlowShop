package com.microservice.checkout.service.impl;

import com.microservice.checkout.client.IProductClient;
import com.microservice.checkout.dto.ProductDTO;
import com.microservice.checkout.service.IProductService;
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
