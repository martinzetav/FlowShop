package com.microservice.checkout.service;

import com.microservice.checkout.dto.request.CartItemRequestDTO;
import com.microservice.checkout.dto.request.CartRequestDTO;
import com.microservice.checkout.dto.response.CartResponseDTO;

import java.util.List;

public interface ICartService{
    CartResponseDTO save(CartRequestDTO cartRequestDTO);
    List<CartResponseDTO> findAll();
    CartResponseDTO findById(Long id);
    CartResponseDTO update(Long id, CartRequestDTO cartRequestDTO);
    void delete(Long id);
    CartResponseDTO addItemToCart(Long cartId, CartItemRequestDTO newItem);
    CartResponseDTO updateItem(Long cartId, Long itemId, CartItemRequestDTO updatedItem);
}
