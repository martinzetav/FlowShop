package com.microservice.checkout.service;

import com.microservice.checkout.dto.CartDTO;
import com.microservice.checkout.exception.ResourceAlreadyExistsException;
import com.microservice.checkout.exception.ResourceNotFoundException;
import com.microservice.checkout.model.Cart;
import com.microservice.checkout.model.CartItem;

import java.util.List;

public interface ICartService{
    CartDTO save(Cart cart);
    List<CartDTO> findAll();
    CartDTO findById(Long id) throws ResourceNotFoundException;
    CartDTO update(Long id, Cart cart) throws ResourceNotFoundException;
    void delete(Long id) throws ResourceNotFoundException;
    CartDTO addItemToCart(Long cartId, CartItem newItem) throws ResourceNotFoundException, ResourceAlreadyExistsException;
    CartDTO updateItem(Long cartId, Long itemId, CartItem newItem) throws ResourceNotFoundException;
}
