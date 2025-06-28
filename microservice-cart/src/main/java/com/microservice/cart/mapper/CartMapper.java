package com.microservice.cart.mapper;

import com.microservice.cart.dto.request.CartRequestDTO;
import com.microservice.cart.dto.response.CartResponseDTO;
import com.microservice.cart.model.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CartItemMapper.class})
public interface CartMapper {
    CartResponseDTO toResponseDto(Cart cart);
    Cart toEntity(CartRequestDTO cartRequestDTO);
}
