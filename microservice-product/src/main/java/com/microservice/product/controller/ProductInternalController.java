package com.microservice.product.controller;

import com.microservice.product.dto.request.StockUpdateRequest;
import com.microservice.product.dto.response.ProductResponseDTO;
import com.microservice.product.service.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products/internal")
public class ProductInternalController {

    private final IProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(productService.findById(id));
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<Void> subtractStock(@PathVariable Long id,
                                              @RequestBody @Valid StockUpdateRequest stockUpdateRequest){
        productService.subtractStock(id, stockUpdateRequest.quantity());
        return ResponseEntity.noContent().build();
    }


}
