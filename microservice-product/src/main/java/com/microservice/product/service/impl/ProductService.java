package com.microservice.product.service.impl;

import com.flowshop.common.exception.ResourceNotFoundException;
import com.microservice.product.dto.request.ProductRequestDTO;
import com.microservice.product.dto.response.ProductResponseDTO;
import com.microservice.product.mapper.ProductMapper;
import com.microservice.product.model.Product;
import com.microservice.product.repository.IProductRepository;
import com.microservice.product.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final IProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional
    @Override
    public ProductResponseDTO save(ProductRequestDTO productRequestDTO) {
        Product product = productMapper.toEntity(productRequestDTO);
        Product savedProduct = productRepository.save(product);
        return productMapper.toResponseDto(savedProduct);
    }

    @Override
    public Page<ProductResponseDTO> findAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productMapper::toResponseDto);
    }

    @Override
    public ProductResponseDTO findById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            return productMapper.toResponseDto(product.get());
        } else {
            throw new ResourceNotFoundException("Product with id " + id + " not found.");
        }
    }

    @Transactional
    @Override
    public ProductResponseDTO update(Long id, ProductRequestDTO productRequestDTO) {
        Product wantedProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found."));

        Product updatedProduct = productMapper.toEntity(productRequestDTO);

        updatedProduct.setId(wantedProduct.getId());

        productRepository.save(updatedProduct);

        return productMapper.toResponseDto(updatedProduct);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found."));

        productRepository.deleteById(product.getId());
    }
}
