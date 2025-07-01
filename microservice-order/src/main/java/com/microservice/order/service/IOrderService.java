package com.microservice.order.service;

import com.microservice.order.dto.response.OrderResponseDTO;
import com.microservice.order.model.Order;

import java.util.List;
import java.util.Optional;

public interface IOrderService {
    OrderResponseDTO save(Order order);
    List<OrderResponseDTO> findAll();
    Optional<OrderResponseDTO> findById(Long id);
    OrderResponseDTO update(Long id, Order order);
    void delete(Long id);
    OrderResponseDTO createOrder(Long cartId);
}
