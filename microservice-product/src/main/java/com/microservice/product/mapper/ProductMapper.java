package com.microservice.product.mapper;

import com.microservice.product.dto.ProductDTO;
import com.microservice.product.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDto(Product product);
    Product toEntity(ProductDTO productDTO);

}
