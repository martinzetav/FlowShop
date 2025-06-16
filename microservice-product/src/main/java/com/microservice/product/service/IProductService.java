package com.microservice.product.service;

import com.microservice.product.dto.ProductDTO;
import com.microservice.product.exception.ResourceNotFoundException;
import com.microservice.product.model.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    ProductDTO save(Product product);
    List<ProductDTO> findAll();
    ProductDTO findById(Long id) throws ResourceNotFoundException;
    ProductDTO update(Long id, Product product) throws ResourceNotFoundException;
    void delete(Long id) throws ResourceNotFoundException;
}
