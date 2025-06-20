package com.microservice.order.service;

import com.microservice.order.dto.ProductDTO;

public interface IProductService {
    ProductDTO findById(Long id);
}
