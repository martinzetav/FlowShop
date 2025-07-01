package com.microservice.cart.controller;

import com.flowshop.common.api.response.ApiSuccessResponse;
import com.flowshop.common.api.response.PageResponse;
import com.flowshop.common.util.ResponseBuilder;
import com.microservice.cart.dto.request.CartItemRequestDTO;
import com.microservice.cart.dto.request.CartRequestDTO;
import com.microservice.cart.dto.response.CartResponseDTO;
import com.microservice.cart.service.ICartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {

    private final ICartService cartService;

    // Crea un carrito por primera vez.
    @PostMapping
    public ResponseEntity<ApiSuccessResponse<CartResponseDTO>> createCart(@RequestBody @Valid CartRequestDTO cart,
                                                                   HttpServletRequest request,
                                                                   UriComponentsBuilder uriComponentsBuilder){

        CartResponseDTO savedCart = cartService.createCart(cart);
        var uri = uriComponentsBuilder.path("/carts/{id}").buildAndExpand(savedCart.id()).toUri();

        ApiSuccessResponse<CartResponseDTO> response = ResponseBuilder.buildSuccessResponse(
                HttpStatus.CREATED.value(),
                "Cart created successfully",
                savedCart,
                request
        );

        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiSuccessResponse<PageResponse<CartResponseDTO>>> findAllCarts(Pageable pageable,
                                                                             HttpServletRequest request){
        Page<CartResponseDTO> page = cartService.findAllCarts(pageable);
        PageResponse<CartResponseDTO> pageResponse = new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast(),
                page.getNumberOfElements()
        );
        ApiSuccessResponse<PageResponse<CartResponseDTO>> response = ResponseBuilder.buildSuccessResponse(
                HttpStatus.OK.value(),
                "Carts retrieved successfully",
                pageResponse,
                request
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<CartResponseDTO>> findCartById(@PathVariable Long id,
                                                                         HttpServletRequest request){
        CartResponseDTO cart = cartService.findCartById(id);
        ApiSuccessResponse<CartResponseDTO> response = ResponseBuilder.buildSuccessResponse(
                HttpStatus.OK.value(),
                "Cart retrieved successfully",
                cart,
                request
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<CartResponseDTO>> updateCart(@PathVariable Long id,
                                                                          @RequestBody @Valid CartRequestDTO cart,
                                                                          HttpServletRequest request) {
        CartResponseDTO updatedCart = cartService.updateCart(id, cart);
        ApiSuccessResponse<CartResponseDTO> response = ResponseBuilder.buildSuccessResponse(
                HttpStatus.OK.value(),
                "Cart updated successfully",
                updatedCart,
                request
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id){
        cartService.deleteCart(id);
        return ResponseEntity.noContent().build();
    }

    // Agrega un nuevo item al carrito
    @PostMapping("/{id}/items")
    public ResponseEntity<ApiSuccessResponse<CartResponseDTO>> addItemToCart(@PathVariable Long id,
                                                                             @RequestBody @Valid CartItemRequestDTO cartItem,
                                                                             HttpServletRequest request) {
        CartResponseDTO cart = cartService.addItemToCart(id, cartItem);
        ApiSuccessResponse<CartResponseDTO> response = ResponseBuilder.buildSuccessResponse(
                HttpStatus.OK.value(),
                "Product added to cart successfully",
                cart,
                request
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Actualiza un item determinado
    @PutMapping("/{cartId}/items/{itemId}")
    public ResponseEntity<ApiSuccessResponse<CartResponseDTO>> updateItem(@PathVariable Long cartId, @PathVariable Long itemId,
                                                      @RequestBody @Valid CartItemRequestDTO updatedItem,
                                                      HttpServletRequest request){

        CartResponseDTO cart = cartService.updateItem(cartId, itemId, updatedItem);
        ApiSuccessResponse<CartResponseDTO> response = ResponseBuilder.buildSuccessResponse(
                HttpStatus.OK.value(),
                "Cart product updated successfully",
                cart,
                request
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // elimina un item determinado
    @DeleteMapping("/{cartId}/items/{itemId}")
    public ResponseEntity<ApiSuccessResponse<CartResponseDTO>> deleteItemToCart(@PathVariable Long cartId,
                                                                                @PathVariable Long itemId,
                                                                                HttpServletRequest request){
        CartResponseDTO cart = cartService.deleteItemToCart(cartId, itemId);
        ApiSuccessResponse<CartResponseDTO> response = ResponseBuilder.buildSuccessResponse(
                HttpStatus.OK.value(),
                "Cart product removed successfully",
                cart,
                request
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<ApiSuccessResponse<CartResponseDTO>> clearCart(@PathVariable Long cartId,
                                                                         HttpServletRequest request){
        CartResponseDTO cart = cartService.clearCart(cartId);
        ApiSuccessResponse<CartResponseDTO> response = ResponseBuilder.buildSuccessResponse(
                HttpStatus.OK.value(),
                "Cart clear successfully",
                cart,
                request
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
