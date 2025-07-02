package com.microservice.cart.controller;

import com.microservice.cart.dto.response.CartResponseDTO;
import com.microservice.cart.service.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carts/internal")
public class CartInternalController {

    private final ICartService cartService;

    @GetMapping("/{id}")
    public ResponseEntity<CartResponseDTO> findCartById(@PathVariable Long id){
        return ResponseEntity.ok(cartService.findCartById(id));
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<Void> completeCart(@PathVariable Long id){
        cartService.completeCart(id);
        return ResponseEntity.noContent().build();
    }



}
