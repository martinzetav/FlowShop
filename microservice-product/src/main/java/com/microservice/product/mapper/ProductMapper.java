package com.microservice.product.mapper;

import com.microservice.product.dto.request.ProductRequestDTO;
import com.microservice.product.dto.response.ProductResponseDTO;
import com.microservice.product.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponseDTO toResponseDto(Product product);
    Product toEntity(ProductRequestDTO productRequestDTO);

}
