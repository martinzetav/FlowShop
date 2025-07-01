package com.microservice.order.service;

import com.microservice.order.dto.CartDTO;

public interface ICartService {
    CartDTO findById(Long id);
    void completeCart(Long cartId);
}
