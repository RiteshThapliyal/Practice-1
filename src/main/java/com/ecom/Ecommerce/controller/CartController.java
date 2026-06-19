package com.ecom.Ecommerce.controller;

import com.ecom.Ecommerce.dto.CartItemRequest;
import com.ecom.Ecommerce.dto.CartItemResponse;
import com.ecom.Ecommerce.entity.CartItem;
import com.ecom.Ecommerce.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartItemService cartItemService;

    @PostMapping
    public ResponseEntity<String> addToCart(@RequestHeader("X-User-ID") String userID, @RequestBody CartItemRequest request)
    {
        boolean cartAdded = cartItemService.addToCart(userID, request);

        if (cartAdded)
        {
            return ResponseEntity.status(HttpStatus.CREATED).body("Cart added successfully");
        }

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Cart not added");
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<String> removeFromCart(@RequestHeader("X-User-ID") String userID, @PathVariable Long productId)
    {
        boolean deleted = cartItemService.deleteItemFromCart(userID, productId);

        return deleted ? ResponseEntity.status(HttpStatus.OK).body("Successfully deleted") : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found");
    }

    @GetMapping("/{productId}")
    public ResponseEntity<List<CartItemResponse>> getCartItems(@RequestHeader("X-User-ID") String userID, @PathVariable Long productId)
    {
        List<CartItemResponse> responses = cartItemService.getCartItems(userID, productId);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
