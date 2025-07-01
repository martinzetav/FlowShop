package com.microservice.order.controller;

import com.flowshop.common.api.response.ApiSuccessResponse;
import com.flowshop.common.util.ResponseBuilder;
import com.microservice.order.dto.response.OrderResponseDTO;
import com.microservice.order.service.IOrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;

    @PostMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<OrderResponseDTO>> createOrder(@PathVariable Long id,
                                                                            HttpServletRequest request,
                                                                            UriComponentsBuilder uriComponentsBuilder){
        OrderResponseDTO savedOrder = orderService.createOrder(id);
        var uri = uriComponentsBuilder.path("/orders/{id}").buildAndExpand(savedOrder.id()).toUri();

        ApiSuccessResponse<OrderResponseDTO> response = ResponseBuilder.buildSuccessResponse(
                HttpStatus.CREATED.value(),
                "Order created successfully",
                savedOrder,
                request
        );
        return ResponseEntity.created(uri).body(response);
    }

}
