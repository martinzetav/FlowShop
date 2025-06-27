package com.microservice.product.service;

import com.microservice.product.dto.request.ProductRequestDTO;
import com.microservice.product.dto.response.ProductResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IProductService {
    ProductResponseDTO save(ProductRequestDTO productRequestDTO);
    Page<ProductResponseDTO> findAll(Pageable pageable);
    ProductResponseDTO findById(Long id);
    ProductResponseDTO update(Long id, ProductRequestDTO productRequestDTO);
    void delete(Long id);
}
