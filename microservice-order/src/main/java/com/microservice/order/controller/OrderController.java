package com.microservice.order.controller;

import com.microservice.order.dto.OrderDTO;
import com.microservice.order.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;

    @PostMapping("/{id}")
    public ResponseEntity<OrderDTO> createOrder(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(id));
    }

}
