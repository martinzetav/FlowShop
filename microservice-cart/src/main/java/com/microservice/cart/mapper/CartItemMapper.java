package com.microservice.cart.mapper;

import com.microservice.cart.dto.request.CartItemRequestDTO;
import com.microservice.cart.dto.response.CartItemResponseDTO;
import com.microservice.cart.model.CartItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    CartItemResponseDTO toResponseDto(CartItem cartItem);
    CartItem toEntity(CartItemRequestDTO cartItemRequestDTO);
}
