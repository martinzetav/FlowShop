package com.microservice.order.service;

import com.microservice.order.dto.ProductDTO;
import com.microservice.order.dto.StockUpdateRequest;

public interface IProductService {
    ProductDTO findById(Long id);
    void subtractStock(Long id, StockUpdateRequest stockUpdateRequest);

}
