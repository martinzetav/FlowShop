package com.microservice.product.service.impl;

import com.flowshop.common.exception.InsufficientStockException;
import com.flowshop.common.exception.InvalidStockOperationException;
import com.flowshop.common.exception.ResourceNotFoundException;
import com.microservice.product.dto.request.ProductRequestDTO;
import com.microservice.product.dto.request.StockUpdateRequest;
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


@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final IProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional
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
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found."));

        return productMapper.toResponseDto(product);
    }

    @Override
    @Transactional
    public ProductResponseDTO update(Long id, ProductRequestDTO productRequestDTO) {
        Product wantedProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found."));

        Product updatedProduct = productMapper.toEntity(productRequestDTO);

        updatedProduct.setId(wantedProduct.getId());

        productRepository.save(updatedProduct);

        return productMapper.toResponseDto(updatedProduct);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found."));

        productRepository.deleteById(product.getId());
    }

    @Override
    @Transactional
    public void updateStock(Long id, StockUpdateRequest stockUpdateRequest){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found."));

        switch (stockUpdateRequest.operation().toUpperCase()) {
            case "ADD" -> addStock(product, stockUpdateRequest.quantity());
            case "SUBTRACT" -> subtractStock(product, stockUpdateRequest.quantity());
            default -> throw new InvalidStockOperationException("Invalid operation value. Allowed values are ADD or SUBTRACT.");
        }

    }

    private void subtractStock(Product product, Integer quantity){
        if(product.getStock() < quantity){
            throw new InsufficientStockException("Not enough stock available for product ID " + product.getId());
        }

        product.setStock(product.getStock() - quantity);
    }

    private void addStock(Product product, Integer quantity) {
        product.setStock(product.getStock() + quantity);
    }
}
