package com.microservice.checkout.mapper;

import com.microservice.checkout.dto.CartDTO;
import com.microservice.checkout.model.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CartItemMapper.class})
public interface CartMapper {
    CartDTO toDto(Cart cart);
    Cart toEntity(CartDTO cartDTO);
}
