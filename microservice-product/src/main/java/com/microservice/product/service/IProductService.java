package com.microservice.product.service;

import com.microservice.product.dto.request.ProductRequestDTO;
import com.microservice.product.dto.response.ProductResponseDTO;
import com.microservice.product.exception.ResourceNotFoundException;
import com.microservice.product.model.Product;

import java.util.List;

public interface IProductService {
    ProductResponseDTO save(ProductRequestDTO productRequestDTO);
    List<ProductResponseDTO> findAll();
    ProductResponseDTO findById(Long id);
    ProductResponseDTO update(Long id, ProductRequestDTO productRequestDTO);
    void delete(Long id);
}
