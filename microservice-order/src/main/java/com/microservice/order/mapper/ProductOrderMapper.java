package com.microservice.order.mapper;

import com.microservice.order.dto.request.ProductOrderRequestDTO;
import com.microservice.order.dto.response.ProductOrderResponseDTO;
import com.microservice.order.model.ProductOrder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductOrderMapper {
    ProductOrderResponseDTO toResponseDto(ProductOrder productOrder);
    ProductOrder toEntity(ProductOrderRequestDTO productOrderRequestDTO);
}
