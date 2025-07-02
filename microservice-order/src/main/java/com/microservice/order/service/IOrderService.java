package com.microservice.order.service;

import com.microservice.order.dto.response.OrderResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IOrderService {
    Page<OrderResponseDTO> findAllOrders(Pageable pageable);
    OrderResponseDTO findOrderById(Long orderId);
    Page<OrderResponseDTO> findAllOrdersByUserId(Pageable pageable, Long userId);
    OrderResponseDTO createOrder(Long cartId);
    void markOrderAsCompleted(Long orderId);
    void cancelOrder(Long orderId);
}
