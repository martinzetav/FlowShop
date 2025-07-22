package com.microservice.cart.controller;

import com.flowshop.common.api.response.ApiSuccessResponse;
import com.flowshop.common.api.response.PageResponse;
import com.flowshop.common.util.ResponseBuilder;
import com.microservice.cart.dto.request.CartItemRequestDTO;
import com.microservice.cart.dto.request.CartRequestDTO;
import com.microservice.cart.dto.response.CartResponseDTO;
import com.microservice.cart.service.ICartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Create a new cart", description = "Creates a new shopping cart for the user with the provided items.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cart created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid or missing data"),
            @ApiResponse(responseCode = "404", description = "User or product not found"),
            @ApiResponse(responseCode = "409", description = "User already has an active cart or product already in cart")
    })
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

    @Operation(summary = "Retrieve all carts", description = "Fetches all carts in paginated format.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carts retrieved successfully")
    })
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

    @Operation(summary = "Get cart by ID", description = "Retrieves detailed information of a specific cart.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
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

    @Operation(summary = "Get active cart by user ID", description = "Retrieves the active cart associated with a user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No active cart found for user")
    })
    @GetMapping("/users/{id}")
    public ResponseEntity<ApiSuccessResponse<CartResponseDTO>> findActiveCartByUserId(@PathVariable Long id,
                                                                            HttpServletRequest request){
        CartResponseDTO cart = cartService.findActiveCartByUserId(id);
        ApiSuccessResponse<CartResponseDTO> response = ResponseBuilder.buildSuccessResponse(
                HttpStatus.OK.value(),
                "Cart retrieved successfully",
                cart,
                request
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Update a cart", description = "Updates all details of an existing cart.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart updated successfully"),
            @ApiResponse(responseCode = "404", description = "Cart not found"),
            @ApiResponse(responseCode = "400", description = "Invalid or missing data")
    })
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

    @Operation(summary = "Update a cart", description = "Updates all details of an existing cart.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart updated successfully"),
            @ApiResponse(responseCode = "404", description = "Cart not found"),
            @ApiResponse(responseCode = "400", description = "Invalid or missing data")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id){
        cartService.deleteCart(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Add product to cart", description = "Adds a new product item to the existing cart.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product added successfully"),
            @ApiResponse(responseCode = "404", description = "Cart or product not found"),
            @ApiResponse(responseCode = "409", description = "Product already exists in cart or insufficient stock")
    })
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

    @Operation(summary = "Update cart item", description = "Updates quantity or product reference of a specific cart item.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart item updated successfully"),
            @ApiResponse(responseCode = "404", description = "Item or cart not found"),
            @ApiResponse(responseCode = "409", description = "Duplicate item or insufficient stock")
    })
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

    @Operation(summary = "Delete cart item", description = "Removes a product item from the cart.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item removed successfully"),
            @ApiResponse(responseCode = "404", description = "Item or cart not found")
    })
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

    @Operation(summary = "Clear cart items", description = "Removes all product items from a specific cart.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart cleared successfully"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
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
