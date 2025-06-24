package com.microservice.checkout.mapper;

import com.microservice.checkout.dto.request.CartItemRequestDTO;
import com.microservice.checkout.dto.response.CartItemResponseDTO;
import com.microservice.checkout.model.CartItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    CartItemResponseDTO toResponseDto(CartItem cartItem);
    CartItem toEntity(CartItemRequestDTO cartItemRequestDTO);
}
