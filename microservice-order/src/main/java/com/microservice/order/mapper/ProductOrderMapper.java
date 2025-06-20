package com.microservice.order.mapper;

import com.microservice.order.dto.ProductOrderDTO;
import com.microservice.order.model.ProductOrder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductOrderMapper {
    ProductOrderDTO toDto(ProductOrder productOrder);
    ProductOrder toEntity(ProductOrderDTO productOrderDTO);
}
