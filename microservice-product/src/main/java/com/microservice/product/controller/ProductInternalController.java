package com.microservice.product.controller;

import com.microservice.product.dto.response.ProductResponseDTO;
import com.microservice.product.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products/internal")
public class ProductInternalController {

    private final IProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findById(Long id){
        return ResponseEntity.ok(productService.findById(id));
    }
}
