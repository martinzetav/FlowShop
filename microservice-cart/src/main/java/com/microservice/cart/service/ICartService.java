package com.microservice.cart.service;

import com.microservice.cart.dto.request.CartItemRequestDTO;
import com.microservice.cart.dto.request.CartRequestDTO;
import com.microservice.cart.dto.response.CartResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICartService{
    CartResponseDTO createCart(CartRequestDTO cartRequestDTO);
    Page<CartResponseDTO> findAllCarts(Pageable pageable);
    CartResponseDTO findCartById(Long id);
    CartResponseDTO updateCart(Long id, CartRequestDTO cartRequestDTO);
    void deleteCart(Long id);
    CartResponseDTO addItemToCart(Long cartId, CartItemRequestDTO newItem);
    CartResponseDTO updateItem(Long cartId, Long itemId, CartItemRequestDTO updatedItem);
    CartResponseDTO deleteItemToCart(Long idCart, Long idItem);
    CartResponseDTO clearCart(Long cartId);
}
