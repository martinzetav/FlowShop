package com.microservice.checkout.mapper;

import com.microservice.checkout.dto.CartItemDTO;
import com.microservice.checkout.model.CartItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    CartItemDTO toDto(CartItem cartItem);
    CartItem toEntity(CartItemDTO cartItemDTO);
}
