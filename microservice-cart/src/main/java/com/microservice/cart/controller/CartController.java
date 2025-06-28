package com.microservice.cart.controller;

import com.microservice.cart.dto.request.CartItemRequestDTO;
import com.microservice.cart.dto.request.CartRequestDTO;
import com.microservice.cart.dto.response.CartResponseDTO;
import com.microservice.cart.service.ICartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {

    private final ICartService cartService;

    // Crea un carrito por primera vez con o sin productos.
    @PostMapping
    public ResponseEntity<CartResponseDTO> save(@RequestBody @Valid CartRequestDTO cart){
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.save(cart));
    }

    @GetMapping
    public ResponseEntity<List<CartResponseDTO>> findAll(){
        return ResponseEntity.ok(cartService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartResponseDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(cartService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartResponseDTO> update(@PathVariable Long id, @RequestBody CartRequestDTO cart) {
        return ResponseEntity.ok(cartService.update(id, cart));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        cartService.delete(id);
        return ResponseEntity.ok("Cart with id " + id + " successfully removed.");
    }

    // Agrega un nuevo item al carrito
    @PostMapping("/{id}/items")
    public ResponseEntity<CartResponseDTO> addItemToCart(@PathVariable Long id, @RequestBody CartItemRequestDTO cartItem) {
        return ResponseEntity.ok(cartService.addItemToCart(id, cartItem));
    }

    // Actualiza un item determinado
    @PutMapping("/{cartId}/items/{itemId}")
    public ResponseEntity<CartResponseDTO> updateItem(@PathVariable Long cartId, @PathVariable Long itemId,
                                                      @RequestBody CartItemRequestDTO updatedItem){
        return ResponseEntity.ok(cartService.updateItem(cartId, itemId, updatedItem));
    }


}
