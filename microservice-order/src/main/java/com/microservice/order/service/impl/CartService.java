package com.microservice.order.service.impl;

import com.microservice.order.client.ICartClient;
import com.microservice.order.dto.CartDTO;
import com.microservice.order.service.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

    private final ICartClient cartClient;

    @Override
    public CartDTO findById(Long id) {
        return cartClient.getCartById(id);
    }
}
