package com.bazarweb.bazarweb.controller; 
 
import java.util.List; 
 
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.PostMapping; 
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RequestParam; 
import org.springframework.web.bind.annotation.RestController; 
 
import com.bazarweb.bazarweb.DTO.AddToCartRequest; 
import com.bazarweb.bazarweb.DTO.CartDTO; 
import com.bazarweb.bazarweb.DTO.CartItemDTO; 
import com.bazarweb.bazarweb.DTO.ClearCartRequest; 
import com.bazarweb.bazarweb.model.Cart; 
import com.bazarweb.bazarweb.service.CartService; 
 
 
 
@RestController 
@RequestMapping("/api/cart") 
public class CartController { 
 
    private final CartService cartService; 
 
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
    public ResponseEntity<CartDTO> addToCart(@RequestBody AddToCartRequest request) { 
        Cart cart = cartService.addToCart(request.getEmail(), request.getProductId(), request.getQuantity()); 
        return ResponseEntity.ok(toDTO(cart)); 
    } 
     
    @PostMapping("/clear") 
    public ResponseEntity<CartDTO> clearCart(@RequestBody ClearCartRequest request) { 
        Cart cart = cartService.clearCart(request.getEmail()); 
        return ResponseEntity.ok(toDTO(cart)); 
    } 
     
}