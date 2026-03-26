package com.pharma.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pharma.order.dto.CartItemRequest;
import com.pharma.order.dto.CartItemResponse;
import com.pharma.order.service.CartService;
 
@RestController
@RequestMapping("/api/cart")
public class CartController {
 
    @Autowired
    private CartService cartService;
 
    // GET /api/orders/cart/42
    @GetMapping("/{customerId}")
    public ResponseEntity<List<CartItemResponse>> getCart(@PathVariable Long customerId) {
        return ResponseEntity.ok(cartService.getCart(customerId));
    }
 
    // POST /api/orders/cart   Body: { "customerId": 42, "medicineId": 5, "quantity": 2 }
    @PostMapping
    public ResponseEntity<CartItemResponse> addToCart(@RequestBody CartItemRequest request) {
        return ResponseEntity.ok(cartService.addToCart(request));
    }
 
    // DELETE /api/orders/cart/42/medicine/5
    @DeleteMapping("/{customerId}/medicine/{medicineId}")
    public ResponseEntity<Void> removeItem(@PathVariable Long customerId,
                                            @PathVariable Long medicineId) {
        cartService.removeItem(customerId, medicineId);
        return ResponseEntity.noContent().build();
    }
 
    // DELETE /api/orders/cart/42
    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long customerId) {
        cartService.clearCart(customerId);
        return ResponseEntity.noContent().build();
    }
}