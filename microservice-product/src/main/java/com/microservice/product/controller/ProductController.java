package com.microservice.product.controller;

import com.flowshop.common.api.response.ApiSuccessResponse;
import com.flowshop.common.api.response.PageResponse;
import com.flowshop.common.util.ResponseBuilder;
import com.microservice.product.dto.request.ProductRequestDTO;
import com.microservice.product.dto.response.ProductResponseDTO;
import com.microservice.product.service.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final IProductService productService;

    @Operation(
            summary = "Create a new product",
            description = "Registers a new product in the catalog. Only accessible to admins."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid or missing product data")
    })
    @PostMapping
    public ResponseEntity<ApiSuccessResponse<ProductResponseDTO>> save(@RequestBody @Valid ProductRequestDTO product,
                                                                       HttpServletRequest request,
                                                                       UriComponentsBuilder uriComponentsBuilder){
        ProductResponseDTO savedProduct = productService.save(product);

        var uri = uriComponentsBuilder.path("/products/{id}").buildAndExpand(savedProduct.id()).toUri();

        ApiSuccessResponse<ProductResponseDTO> response = ResponseBuilder.buildSuccessResponse(
                HttpStatus.CREATED.value(),
                "Product created successfully",
                savedProduct,
                request
        );

        return ResponseEntity.created(uri).body(response);
    }

    @Operation(
            summary = "Get all products",
            description = "Retrieves all products available in the catalog in paginated format."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<ApiSuccessResponse<PageResponse<ProductResponseDTO>>> findAll(@PageableDefault(size = 3, sort = {"name"}) Pageable pageable,
                                                                                        HttpServletRequest request){
        Page<ProductResponseDTO> page = productService.findAll(pageable);

        PageResponse<ProductResponseDTO> pageResponse = new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast(),
                page.getNumberOfElements()
        );

        ApiSuccessResponse<PageResponse<ProductResponseDTO>> response = ResponseBuilder.buildSuccessResponse(
                HttpStatus.OK.value(),
                "Products retrieved successfully",
                pageResponse,
                request
        );

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get product by ID",
            description = "Retrieves detailed information about a product by its ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
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

    @Operation(
            summary = "Update a product",
            description = "Edits the product data. Accessible only to admins."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "400", description = "Invalid or missing product data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<ProductResponseDTO>> update(@PathVariable Long id, @RequestBody @Valid ProductRequestDTO product,
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

    @Operation(
            summary = "Delete a product",
            description = "Removes a product from the catalog by its ID. Accessible only to admins."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
