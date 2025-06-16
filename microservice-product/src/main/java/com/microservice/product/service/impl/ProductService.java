package com.microservice.product.service.impl;

import com.microservice.product.dto.ProductDTO;
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
    public ProductDTO save(Product product) {
        Product savedProduct = productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }

    @Override
    public List<ProductDTO> findAll() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    public ProductDTO findById(Long id) throws ResourceNotFoundException {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            return productMapper.toDto(product.get());
        } else {
            throw new ResourceNotFoundException("Product with id " + id + " not found.");
        }
    }

    @Override
    public ProductDTO update(Long id, Product product) throws ResourceNotFoundException {
        Optional<Product> wantedProduct = productRepository.findById(id);
        if(wantedProduct.isPresent()){
            Product updatedProduct = wantedProduct.get();
            updatedProduct.setName(product.getName());
            updatedProduct.setBrand(product.getBrand());
            updatedProduct.setDescription(product.getDescription());
            updatedProduct.setPrice(product.getPrice());
            updatedProduct.setStock(product.getStock());
            productRepository.save(updatedProduct);
            return productMapper.toDto(updatedProduct);
        } else {
            throw new ResourceNotFoundException("Product with id " + id + " not found.");
        }
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found."));

        productRepository.deleteById(product.getId());
    }
}
