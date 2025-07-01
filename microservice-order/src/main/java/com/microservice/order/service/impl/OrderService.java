package com.microservice.order.service.impl;

import com.flowshop.common.exception.InsufficientStockException;
import com.flowshop.common.exception.ResourceNotFoundException;
import com.microservice.order.dto.CartDTO;
import com.microservice.order.dto.ProductDTO;
import com.microservice.order.dto.response.OrderResponseDTO;
import com.microservice.order.mapper.OrderMapper;
import com.microservice.order.model.Order;
import com.microservice.order.model.OrderStatus;
import com.microservice.order.model.ProductOrder;
import com.microservice.order.repository.IOrderRepository;
import com.microservice.order.service.ICartService;
import com.microservice.order.service.IOrderService;
import com.microservice.order.service.IProductService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final ICartService cartService;

    @Override
    public OrderResponseDTO save(Order order) {
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toResponseDto(order);
    }

    @Override
    public List<OrderResponseDTO> findAll() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(orderMapper::toResponseDto)
                .toList();
    }

    @Override
    public Optional<OrderResponseDTO> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public OrderResponseDTO update(Long id, Order order) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    @Transactional
    public OrderResponseDTO createOrder(Long cartId){
        CartDTO cart;

        try{
            cart = cartService.findById(cartId);
        } catch (FeignException.NotFound e){
            throw new ResourceNotFoundException("Cart with id " + cartId + " not found.");
        }

        List<ProductOrder> productOrders = new ArrayList<>();

        cart.items().forEach(item -> {

            ProductDTO product;

            try {
                product = productService.findById(item.productId());

                if(item.quantity() > product.stock()){
                    throw new InsufficientStockException("Insufficient stock for product ID: " + product.id());
                }
            } catch (FeignException.NotFound e) {
                throw new ResourceNotFoundException("Product with id " + item.productId() + " not found.");
            }

            // implementar para restar el stock del producto.

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
                .userId(cart.userId())
                .date(LocalDateTime.now())
                .status(OrderStatus.PENDING)
                .orders(productOrders)
                .totalPrice(total)
                .build();

        productOrders.forEach(po -> po.setOrder(order));

        Order savedOrder = orderRepository.save(order);

        return orderMapper.toResponseDto(savedOrder);
    }
}
