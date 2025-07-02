package com.microservice.order.service;

import com.microservice.order.dto.ProductDTO;
import com.microservice.order.dto.StockUpdateRequest;

public interface IProductService {
    ProductDTO findById(Long id);
    void updateStock(Long id, StockUpdateRequest stockUpdateRequest);
}
