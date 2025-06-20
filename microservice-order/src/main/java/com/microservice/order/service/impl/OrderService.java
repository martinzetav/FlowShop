package com.microservice.order.service.impl;

import com.microservice.order.dto.OrderDTO;
import com.microservice.order.dto.ProductDTO;
import com.microservice.order.dto.request.OrderRequestDTO;
import com.microservice.order.exception.InsufficientStockException;
import com.microservice.order.mapper.OrderMapper;
import com.microservice.order.model.Order;
import com.microservice.order.model.OrderStatus;
import com.microservice.order.model.ProductOrder;
import com.microservice.order.repository.IOrderRepository;
import com.microservice.order.service.IOrderService;
import com.microservice.order.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final IOrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final IProductService productService;

    @Override
    public OrderDTO save(Order order) {
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderDTO> findAll() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public Optional<OrderDTO> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public OrderDTO update(Long id, Order order) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public OrderDTO createOrder(OrderRequestDTO orderRequestDTO) {
        List<ProductOrder> productOrders = new ArrayList<>();

        orderRequestDTO.orders().forEach(item -> {
            ProductDTO product = productService.findById(item.productId());

            if(item.quantity() > product.stock()){
                throw new InsufficientStockException("Insufficient stock for product ID: " + product.id());
            }

            ProductOrder productOrder = ProductOrder.builder()
                    .productId(product.id())
                    .quantity(item.quantity())
                    .price(product.price())
                    .build();

            productOrders.add(productOrder);
        });

        Double total = productOrders.stream()
                .mapToDouble(po -> po.getPrice() * po.getQuantity())
                .sum();

        Order order = Order.builder()
                .userId(orderRequestDTO.userId())
                .date(LocalDateTime.now())
                .status(OrderStatus.PENDING)
                .orders(productOrders)
                .totalPrice(total)
                .build();

        productOrders.forEach(po -> po.setOrder(order));

        Order savedOrder = orderRepository.save(order);

        return orderMapper.toDto(order);
    }
}
