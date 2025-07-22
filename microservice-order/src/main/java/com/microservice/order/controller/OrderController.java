package com.microservice.order.controller;

import com.flowshop.common.api.response.ApiSuccessResponse;
import com.flowshop.common.api.response.PageResponse;
import com.flowshop.common.util.ResponseBuilder;
import com.microservice.order.dto.response.OrderResponseDTO;
import com.microservice.order.service.IOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;

    @Operation(
            summary = "Create order from cart",
            description = "Generates an order based on the items of a valid shopping cart and subtracts stock. Only carts in ACTIVE state are allowed."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created successfully"),
            @ApiResponse(responseCode = "404", description = "Cart or product not found"),
            @ApiResponse(responseCode = "409", description = "Cart already completed or insufficient stock")
    })
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

    @Operation(
            summary = "Get all orders (paginated)",
            description = "Retrieves a paginated list of all orders in the system. Accessible only to admins."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<ApiSuccessResponse<PageResponse<OrderResponseDTO>>> findAllOrders(@PageableDefault(size = 10, sort = {"date"},
                                                                                            direction = Sort.Direction.DESC) Pageable pageable,
                                                                                            HttpServletRequest request){
        Page<OrderResponseDTO> page = orderService.findAllOrders(pageable);
        PageResponse<OrderResponseDTO> pageResponse = new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast(),
                page.getNumberOfElements()
        );

        ApiSuccessResponse<PageResponse<OrderResponseDTO>> response = ResponseBuilder.buildSuccessResponse(
                HttpStatus.OK.value(),
                "Orders retrieved successfully",
                pageResponse,
                request
        );

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get order by ID",
            description = "Fetches a specific order by its ID. Accessible to admins and the order's user."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<OrderResponseDTO>> findOrderById(@PathVariable Long id,
                                                                              HttpServletRequest request){
        OrderResponseDTO order = orderService.findOrderById(id);
        ApiSuccessResponse<OrderResponseDTO> response = ResponseBuilder.buildSuccessResponse(
                HttpStatus.OK.value(),
                "Order retrieved successfully",
                order,
                request
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(
            summary = "Get user orders",
            description = "Retrieves all orders for a given user ID in paginated format."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/users/{id}")
    public ResponseEntity<ApiSuccessResponse<PageResponse<OrderResponseDTO>>> findAllOrdersByUserId(@PageableDefault(size = 10, sort = {"date"},
                                                                                                    direction = Sort.Direction.DESC) Pageable pageable,
                                                                                                    @PathVariable Long id,
                                                                                                    HttpServletRequest request){
        Page<OrderResponseDTO> page = orderService.findAllOrdersByUserId(pageable, id);
        PageResponse<OrderResponseDTO> pageResponse = new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast(),
                page.getNumberOfElements()
        );

        ApiSuccessResponse<PageResponse<OrderResponseDTO>> response = ResponseBuilder.buildSuccessResponse(
                HttpStatus.OK.value(),
                "Orders retrieved successfully",
                pageResponse,
                request
        );

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Mark order as completed",
            description = "Updates the status of an order to COMPLETED. Does nothing if already completed."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Order marked as completed"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PatchMapping("/{id}/complete")
    public ResponseEntity<Void> makeOrderAsCompleted(@PathVariable Long id){
        orderService.markOrderAsCompleted(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Cancel order",
            description = "Updates the status of an order to CANCELLED and reverts the product stock."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Order cancelled"),
            @ApiResponse(responseCode = "404", description = "Order or product not found"),
            @ApiResponse(responseCode = "409", description = "Insufficient stock to revert cancellation")
    })
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long id){
        orderService.cancelOrder(id);
        return ResponseEntity.noContent().build();
    }



}
