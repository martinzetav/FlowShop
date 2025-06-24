package com.microservice.product.service.impl;

import com.microservice.product.dto.request.ProductRequestDTO;
import com.microservice.product.dto.response.ProductResponseDTO;
import com.microservice.product.exception.ResourceNotFoundException;
import com.microservice.product.mapper.ProductMapper;
import com.microservice.product.model.Product;
import com.microservice.product.repository.IProductRepository;
import com.microservice.product.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final IProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponseDTO save(ProductRequestDTO productRequestDTO) {
        Product product = productMapper.toEntity(productRequestDTO);
        Product savedProduct = productRepository.save(product);
        return productMapper.toResponseDto(savedProduct);
    }

    @Override
    public List<ProductResponseDTO> findAll() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(productMapper::toResponseDto)
                .toList();
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

    @Override
    public ProductResponseDTO update(Long id, ProductRequestDTO productRequestDTO) {
        Optional<Product> wantedProduct = productRepository.findById(id);
        if(wantedProduct.isPresent()){
            Product updatedProduct = wantedProduct.get();
            updatedProduct.setName(productRequestDTO.name());
            updatedProduct.setBrand(productRequestDTO.brand());
            updatedProduct.setDescription(productRequestDTO.description());
            updatedProduct.setPrice(productRequestDTO.price());
            updatedProduct.setStock(productRequestDTO.stock());
            productRepository.save(updatedProduct);
            return productMapper.toResponseDto(updatedProduct);
        } else {
            throw new ResourceNotFoundException("Product with id " + id + " not found.");
        }
    }

    @Override
    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found."));

        productRepository.deleteById(product.getId());
    }
}
