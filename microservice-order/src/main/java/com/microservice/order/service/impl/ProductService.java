package com.microservice.order.service.impl;

import com.microservice.order.client.IProductClient;
import com.microservice.order.dto.ProductDTO;
import com.microservice.order.service.IProductService;
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
