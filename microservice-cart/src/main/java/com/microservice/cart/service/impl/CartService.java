package com.microservice.cart.service.impl;

import com.flowshop.common.exception.InsufficientStockException;
import com.flowshop.common.exception.ResourceNotFoundException;
import com.microservice.cart.dto.request.CartItemRequestDTO;
import com.microservice.cart.dto.request.CartRequestDTO;
import com.microservice.cart.dto.response.CartResponseDTO;
import com.microservice.cart.dto.ProductDTO;
import com.microservice.cart.exception.ResourceAlreadyExistsException;
import com.microservice.cart.exception.UserAlreadyHasActiveCartException;
import com.microservice.cart.mapper.CartItemMapper;
import com.microservice.cart.mapper.CartMapper;
import com.microservice.cart.model.Cart;
import com.microservice.cart.model.CartItem;
import com.microservice.cart.model.CartStatus;
import com.microservice.cart.repository.ICartRepository;
import com.microservice.cart.service.ICartService;
import com.microservice.cart.service.IProductService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

    private final CartMapper cartMapper;
    private final CartItemMapper cartItemMapper;
    private final ICartRepository cartRepository;
    private final IProductService productService;

    @Override
    @Transactional
    public CartResponseDTO createCart(CartRequestDTO cartRequestDTO) {
        Optional<Cart> existingCart = cartRepository.findByUserIdAndStatus(cartRequestDTO.userId(), CartStatus.ACTIVE);
        if(existingCart.isPresent()){
            throw new UserAlreadyHasActiveCartException("The user with ID " + cartRequestDTO.userId() +
                    " already has an active shopping cart.");
        }

        cartRequestDTO.items().forEach(item -> {

            try {
                ProductDTO product = productService.findById(item.productId());

                if(item.quantity() > product.stock()){
                    throw new InsufficientStockException("Insufficient stock for product ID: " + product.id());
                }

            } catch (FeignException.NotFound e){
                throw new ResourceNotFoundException("Product with id " + item.productId() + " not found.");
            }
        });

        Cart cart = cartMapper.toEntity(cartRequestDTO);
        cart.setStatus(CartStatus.ACTIVE);
        Cart savedCart = cartRepository.save(cart);
        return cartMapper.toResponseDto(savedCart);
    }

    @Override
    public Page<CartResponseDTO> findAllCarts(Pageable pageable) {
        return cartRepository.findAll(pageable)
                .map(cartMapper::toResponseDto);
    }

    @Override
    public CartResponseDTO findCartById(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart with id " + id + " not found."));

        return cartMapper.toResponseDto(cart);
    }

    @Override
    public CartResponseDTO updateCart(Long id, CartRequestDTO cartRequestDTO) {
        Cart existingCart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart with id " + id + " not found."));

        Cart updatedCart = cartMapper.toEntity(cartRequestDTO);
        updatedCart.setId(existingCart.getId());

        cartRepository.save(updatedCart);

        return cartMapper.toResponseDto(updatedCart);
    }

    @Override
    @Transactional
    public CartResponseDTO addItemToCart(Long cartId, CartItemRequestDTO newItem) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart with id " + cartId + " not found."));

        List<CartItem> items = cart.getItems();

        boolean alreadyExists = items.stream()
                .anyMatch(item -> item.getProductId().equals(newItem.productId()));

        if(alreadyExists){
            throw new ResourceAlreadyExistsException("Product already exists in cart.");
        }

        try{
            ProductDTO product = productService.findById(newItem.productId());

            if(newItem.quantity() > product.stock()){
                throw new InsufficientStockException("Insufficient stock for product ID: " + product.id());
            }

        } catch (FeignException.NotFound e){
            throw new ResourceNotFoundException("Product with id " + newItem.productId() + " not found.");
        }

        CartItem cartItem = cartItemMapper.toEntity(newItem);
        items.add(cartItem);

        Cart cartWithNewItem = cartRepository.save(cart);
        return cartMapper.toResponseDto(cartWithNewItem);
    }

    @Override
    @Transactional
    public CartResponseDTO updateItem(Long cartId, Long itemId, CartItemRequestDTO updatedItem) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart with id " + cartId + " not found."));

        List<CartItem> items = cart.getItems();

        CartItem existingItem = items.stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Item with id " + itemId + " not found in cart."));

        try{
            ProductDTO product = productService.findById(updatedItem.productId());

            items.forEach(item -> {
                if(item.getProductId().equals(updatedItem.productId()) && !item.getId().equals(itemId)){
                    throw new ResourceAlreadyExistsException("Product already exists in cart.");
                }
            });

            if(updatedItem.quantity() > product.stock()){
                throw new InsufficientStockException("Insufficient stock for product ID: " + product.id());
            }

            existingItem.setProductId(updatedItem.productId());
            existingItem.setQuantity(updatedItem.quantity());

        } catch (FeignException.NotFound e){
            throw new ResourceNotFoundException("Product with id " + updatedItem.productId() + " not found.");
        }

        Cart updatedCart = cartRepository.save(cart);
        return cartMapper.toResponseDto(updatedCart);
    }

    @Override
    @Transactional
    public void deleteCart(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart with id " + id + " not found."));

        cartRepository.deleteById(cart.getId());
    }

    @Override
    @Transactional
    public CartResponseDTO deleteItemToCart(Long cartId, Long itemId){
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart with id " + cartId + " not found."));

        boolean removed = cart.getItems().removeIf(item -> item.getId().equals(itemId));

        if(!removed){
            throw new ResourceNotFoundException("Item with id " + itemId + " not found in cart.");
        }

        cartRepository.save(cart);
        return cartMapper.toResponseDto(cart);
    }

    @Override
    @Transactional
    public CartResponseDTO clearCart(Long cartId){
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart with id " + cartId + " not found."));

        cart.getItems().clear();
        cartRepository.save(cart);
        return cartMapper.toResponseDto(cart);
    }

    @Override
    @Transactional
    public void completeCart(Long cartId){
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart with id " + cartId + " not found."));

        cart.setStatus(CartStatus.COMPLETED);
        cartRepository.save(cart);
    }
}
