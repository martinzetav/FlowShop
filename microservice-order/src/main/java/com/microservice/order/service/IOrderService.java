package com.microservice.order.service;

import com.microservice.order.dto.OrderDTO;
import com.microservice.order.model.Order;

import java.util.List;
import java.util.Optional;

public interface IOrderService {
    OrderDTO save(Order order);
    List<OrderDTO> findAll();
    Optional<OrderDTO> findById(Long id);
    OrderDTO update(Long id, Order order);
    void delete(Long id);
    OrderDTO createOrder(Long id);
}
