package com.microservice.order.service.impl;

import com.microservice.order.client.ICartClient;
import com.microservice.order.dto.CartDTO;
import com.microservice.order.exception.CartServiceUnavailableException;
import com.microservice.order.service.ICartService;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

    private final ICartClient cartClient;

    @Override
    @CircuitBreaker(name = "cart", fallbackMethod = "fallbackFindById")
    @Retry(name = "cart")
    public CartDTO findById(Long id) {
        return cartClient.getCartById(id);
    }

    public CartDTO fallbackFindById(Long id, CallNotPermittedException e){
        throw new CartServiceUnavailableException(
                "Cart service is currently unavailable. Please try again later."
        );
    }

    @Override
    @CircuitBreaker(name = "cart", fallbackMethod = "fallbackCompleteCart")
    @Retry(name = "cart")
    public void completeCart(Long cartId) {
        cartClient.completeCart(cartId);
    }

    public void fallbackCompleteCart(Long cartId, CallNotPermittedException e){
        throw new CartServiceUnavailableException(
                "Cart service is currently unavailable. Please try again later."
        );
    }


}
