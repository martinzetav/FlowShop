package com.microservice.cart.service;

import com.microservice.cart.dto.ProductDTO;

public interface IProductService {
    ProductDTO findById(Long id);
}
