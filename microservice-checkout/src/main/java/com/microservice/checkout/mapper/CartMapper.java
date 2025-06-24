package com.microservice.checkout.mapper;

import com.microservice.checkout.dto.request.CartRequestDTO;
import com.microservice.checkout.dto.response.CartResponseDTO;
import com.microservice.checkout.model.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CartItemMapper.class})
public interface CartMapper {
    CartResponseDTO toResponseDto(Cart cart);
    Cart toEntity(CartRequestDTO cartRequestDTO);
}
