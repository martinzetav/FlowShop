package com.microservice.checkout.service.impl;

import com.microservice.checkout.dto.CartDTO;
import com.microservice.checkout.dto.ProductDTO;
import com.microservice.checkout.exception.InsufficientStockException;
import com.microservice.checkout.exception.ResourceAlreadyExistsException;
import com.microservice.checkout.exception.ResourceNotFoundException;
import com.microservice.checkout.mapper.CartMapper;
import com.microservice.checkout.model.Cart;
import com.microservice.checkout.model.CartItem;
import com.microservice.checkout.repository.ICartRepository;
import com.microservice.checkout.service.ICartService;
import com.microservice.checkout.service.IProductService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

    private final ICartRepository cartRepository;
    private final CartMapper cartMapper;
    private final IProductService productService;

    @Override
    public CartDTO save(Cart cart) {
        // agregar validacion mediante USER_ID
        Cart savedCart = cartRepository.save(cart);
        return cartMapper.toDto(savedCart);
    }

    @Override
    public List<CartDTO> findAll() {
        List<Cart> carts = cartRepository.findAll();
        return carts.stream()
                .map(cartMapper::toDto)
                .toList();
    }

    @Override
    public CartDTO findById(Long id) throws ResourceNotFoundException {
        Optional<Cart> cart = cartRepository.findById(id);
        if(cart.isPresent()){
            return cartMapper.toDto(cart.get());
        } else {
            throw new ResourceNotFoundException("Cart with id " + id + " not found.");
        }
    }

    @Override
    public CartDTO update(Long id, Cart cart) throws ResourceNotFoundException {
        Optional<Cart> wantedCart = cartRepository.findById(id);
        if(wantedCart.isPresent()){
            Cart updatedCart = wantedCart.get();
            updatedCart.setUserId(cart.getUserId());
            updatedCart.setItems(cart.getItems());
            cartRepository.save(updatedCart);
            return cartMapper.toDto(updatedCart);
        } else {
            throw new ResourceNotFoundException("Cart with id " + id + " not found.");
        }
    }

    public CartDTO addItemToCart(Long cartId, CartItem newItem) throws ResourceNotFoundException, ResourceAlreadyExistsException {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart with id " + cartId + " not found."));

        List<CartItem> items = cart.getItems();

        Optional<CartItem> existingItemOpt = items.stream()
                .filter(item -> item.getProductId().equals(newItem.getProductId()))
                .findFirst();

        if(existingItemOpt.isPresent()){
            throw new ResourceAlreadyExistsException("Product already exists in cart.");
        }

        try{
            ProductDTO product = productService.findById(newItem.getProductId());

            if(newItem.getQuantity() > product.stock()){
                throw new InsufficientStockException("Insufficient stock for product ID: " + product.id());
            }

        } catch (FeignException.NotFound e){
            throw new ResourceNotFoundException("Product with id " + newItem.getProductId() + " not found.");
        }

        items.add(newItem);
        Cart cartWithNewItem = cartRepository.save(cart);
        return cartMapper.toDto(cartWithNewItem);
    }

    public CartDTO updateItem(Long cartId, Long itemId, CartItem updatedItem) throws ResourceNotFoundException {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart with id " + cartId + " not found."));

        List<CartItem> items = cart.getItems();

        CartItem existingItem = items.stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Item with id " + itemId + " not found in cart."));

        try{
            ProductDTO product = productService.findById(existingItem.getProductId());

            if(updatedItem.getQuantity() > product.stock()){
                throw new InsufficientStockException("Insufficient stock for product ID: " + product.id());
            }

            existingItem.setQuantity(updatedItem.getQuantity());

        } catch (FeignException.NotFound e){
            throw new ResourceNotFoundException("Product with id " + existingItem.getProductId() + " not found.");
        }

        Cart updatedCart = cartRepository.save(cart);
        return cartMapper.toDto(updatedCart);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart with id " + id + " not found."));

        cartRepository.deleteById(cart.getId());
    }
}
