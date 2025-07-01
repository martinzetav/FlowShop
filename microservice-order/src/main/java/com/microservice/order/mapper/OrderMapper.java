package com.microservice.order.mapper;

import com.microservice.order.dto.request.OrderRequestDTO;
import com.microservice.order.dto.response.OrderResponseDTO;
import com.microservice.order.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductOrderMapper.class})
public interface OrderMapper {
    OrderResponseDTO toResponseDto(Order order);
    Order toEntity(OrderRequestDTO orderRequestDTO);
}
