package com.microservice.order.mapper;

import com.microservice.order.dto.OrderDTO;
import com.microservice.order.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductOrderMapper.class})
public interface OrderMapper {
    OrderDTO toDto(Order order);
    Order toEntity(OrderDTO orderDTO);
}
