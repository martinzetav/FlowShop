package com.microservice.product.controller;

import com.microservice.product.api.response.ApiSuccessResponse;
import com.microservice.product.dto.request.ProductRequestDTO;
import com.microservice.product.dto.response.ProductResponseDTO;
import com.microservice.product.exception.ResourceNotFoundException;
import com.microservice.product.model.Product;
import com.microservice.product.service.IProductService;
import com.microservice.product.util.ResponseBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final IProductService productService;

    @PostMapping
    public ResponseEntity<ApiSuccessResponse<ProductResponseDTO>> save(@RequestBody @Valid ProductRequestDTO product,
                                                   HttpServletRequest request){
        ProductResponseDTO savedProduct = productService.save(product);

        ApiSuccessResponse<ProductResponseDTO> response = ResponseBuilder.buildSuccessResponse(
                HttpStatus.CREATED.value(),
                "Product created successfully",
                savedProduct,
                request
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> findAll(){
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable Long id, @RequestBody ProductRequestDTO product) throws ResourceNotFoundException {
        return ResponseEntity.ok(productService.update(id, product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok("Product with id " + id + " successfully removed.");
    }



}
