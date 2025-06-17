package com.microservice.checkout.controller;

import com.microservice.checkout.dto.CartDTO;
import com.microservice.checkout.exception.ResourceAlreadyExistsException;
import com.microservice.checkout.exception.ResourceNotFoundException;
import com.microservice.checkout.model.Cart;
import com.microservice.checkout.model.CartItem;
import com.microservice.checkout.service.ICartService;
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
    public ResponseEntity<CartDTO> save(@RequestBody Cart cart){
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.save(cart));
    }

    @GetMapping
    public ResponseEntity<List<CartDTO>> findAll(){
        return ResponseEntity.ok(cartService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartDTO> findById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(cartService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartDTO> update(@PathVariable Long id, @RequestBody Cart cart) throws ResourceNotFoundException {
        return ResponseEntity.ok(cartService.update(id, cart));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws ResourceNotFoundException {
        cartService.delete(id);
        return ResponseEntity.ok("Cart with id " + id + " successfully removed.");
    }

    // Agrega un nuevo item al carrito
    @PostMapping("/{id}/items")
    public ResponseEntity<CartDTO> addItemToCart(@PathVariable Long id, @RequestBody CartItem cartItem) throws ResourceNotFoundException, ResourceAlreadyExistsException {
        return ResponseEntity.ok(cartService.addItemToCart(id, cartItem));
    }

    // Actualiza un item determinado
    @PutMapping("/{cartId}/items/{itemId}")
    public ResponseEntity<CartDTO> updateItem(@PathVariable Long cartId, @PathVariable Long itemId,
                                              @RequestBody CartItem updatedItem) throws ResourceNotFoundException {
        return ResponseEntity.ok(cartService.updateItem(cartId, itemId, updatedItem));
    }


}
