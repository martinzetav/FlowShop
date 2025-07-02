package com.microservice.order.service.impl;

import com.flowshop.common.exception.InsufficientStockException;
import com.flowshop.common.exception.InvalidStockOperationException;
import com.flowshop.common.exception.ResourceNotFoundException;
import com.microservice.order.dto.CartDTO;
import com.microservice.order.dto.ProductDTO;
import com.microservice.order.dto.StockUpdateRequest;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final IOrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final IProductService productService;
    private final ICartService cartService;

    @Override
    public Page<OrderResponseDTO> findAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(orderMapper::toResponseDto);
    }

    @Override
    public OrderResponseDTO findOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order with id " + orderId + " not found."));
        return orderMapper.toResponseDto(order);
    }

    @Override
    public Page<OrderResponseDTO> findAllOrdersByUserId(Pageable pageable, Long userId){
        return orderRepository.findAllByUserId(userId, pageable)
                .map(orderMapper::toResponseDto);
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

                productService.updateStock(product.id(), new StockUpdateRequest(item.quantity(), "SUBTRACT"));

            } catch (FeignException.NotFound e) {
                throw new ResourceNotFoundException("Product with id " + item.productId() + " not found.");
            } catch (FeignException.BadRequest e){
                throw new InvalidStockOperationException("Invalid operation value. Allowed values are ADD or SUBTRACT.");
            } catch (FeignException.Conflict e){
                throw new InsufficientStockException("Insufficient stock for product ID: " + item.productId());
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
                .userId(cart.userId())
                .date(LocalDateTime.now())
                .status(OrderStatus.PENDING)
                .orders(productOrders)
                .totalPrice(total)
                .build();

        productOrders.forEach(po -> po.setOrder(order));

        Order savedOrder = orderRepository.save(order);

        cartService.completeCart(cart.id());

        return orderMapper.toResponseDto(savedOrder);
    }

    @Override
    @Transactional
    public void markOrderAsCompleted(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order with id " + orderId + " not found."));
        if(order.getStatus() == OrderStatus.COMPLETED) return;
        order.setStatus(OrderStatus.COMPLETED);
    }

    @Override
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order with id " + orderId + " not found."));
        if(order.getStatus() == OrderStatus.CANCELLED) return;
        order.setStatus(OrderStatus.CANCELLED);
        order.getOrders().forEach(productOrder -> {
            try {
                ProductDTO product = productService.findById(productOrder.getProductId());
                productService.updateStock(product.id(), new StockUpdateRequest(productOrder.getQuantity(), "ADD"));
            } catch (FeignException.NotFound e){
                throw new ResourceNotFoundException("Product with id " + productOrder.getProductId() + " not found.");
            } catch (FeignException.BadRequest e){
                throw new InvalidStockOperationException("Invalid operation value. Allowed values are ADD or SUBTRACT.");
            } catch (FeignException.Conflict e){
                throw new InsufficientStockException("Insufficient stock for product ID: " + productOrder.getProductId());
            }
        });
    }
}
