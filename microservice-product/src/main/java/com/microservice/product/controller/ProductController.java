package com.microservice.product.controller;

import com.flowshop.common.api.response.ApiSuccessResponse;
import com.flowshop.common.util.ResponseBuilder;
import com.microservice.product.dto.request.ProductRequestDTO;
import com.microservice.product.dto.response.ProductResponseDTO;
import com.microservice.product.service.IProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ApiSuccessResponse<List<ProductResponseDTO>>> findAll(HttpServletRequest request){
        List<ProductResponseDTO> products = productService.findAll();
        ApiSuccessResponse<List<ProductResponseDTO>> response = ResponseBuilder.buildSuccessResponse(
                HttpStatus.OK.value(),
                "Products retrieved successfully",
                products,
                request
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<ProductResponseDTO>> findById(@PathVariable Long id, HttpServletRequest request) {
        ProductResponseDTO product = productService.findById(id);
        ApiSuccessResponse<ProductResponseDTO> response = ResponseBuilder.buildSuccessResponse(
                HttpStatus.OK.value(),
                "Product retrieved successfully",
                product,
                request
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<ProductResponseDTO>> update(@PathVariable Long id, @RequestBody ProductRequestDTO product,
                                                     HttpServletRequest request){

        ProductResponseDTO updatedProduct = productService.update(id, product);
        ApiSuccessResponse<ProductResponseDTO> response = ResponseBuilder.buildSuccessResponse(
                HttpStatus.OK.value(),
                "Product updated successfully",
                updatedProduct,
                request
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<String>> delete(@PathVariable Long id, HttpServletRequest request) {
        productService.delete(id);
        ApiSuccessResponse<String> response = ResponseBuilder.buildSuccessResponse(
                HttpStatus.OK.value(),
                "Product with id " + id + " successfully removed.",
                null,
                request
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



}
