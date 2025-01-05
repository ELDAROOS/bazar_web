package com.bazarweb.bazarweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bazarweb.bazarweb.DTO.AddToCartRequest;
import com.bazarweb.bazarweb.DTO.CartDTO;
import com.bazarweb.bazarweb.DTO.CartItemDTO;
import com.bazarweb.bazarweb.DTO.ClearCartRequest;
import com.bazarweb.bazarweb.model.Cart;
import com.bazarweb.bazarweb.service.CartService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<CartDTO> getCart(@RequestParam String email) {
        Cart cart = cartService.findOrCreateCartByEmail(email);
        CartDTO cartDTO = toDTO(cart);
        return ResponseEntity.ok(cartDTO);
    }

    private CartDTO toDTO(Cart cart) {
        List<CartItemDTO> items = cart.getItems().stream()
            .map(item -> new CartItemDTO(
                    item.getId(),
                    item.getProduct().getName(),
                    item.getQuantity(),
                    item.getProduct().getPrice()))
            .toList();

        return new CartDTO(cart.getId(), cart.getUser().getEmail(), cart.getTotalPrice(), items);
    }

    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(@RequestBody AddToCartRequest request) {
        Cart cart = cartService.addToCart(request.getEmail(), request.getProductId(), request.getQuantity());
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/clear")
    public ResponseEntity<Cart> clearCart(@RequestBody ClearCartRequest request) {
        Cart cart = cartService.clearCart(request.getEmail());
        return ResponseEntity.ok(cart);
    }
}
