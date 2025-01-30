package com.bazarweb.bazarweb.service;

import org.springframework.stereotype.Service;
import com.bazarweb.bazarweb.enums.ProductStatus;
import com.bazarweb.bazarweb.model.Cart;
import com.bazarweb.bazarweb.model.Product;
import com.bazarweb.bazarweb.model.User;
import com.bazarweb.bazarweb.repository.CartRepository;
import com.bazarweb.bazarweb.repository.ProductRepository;
import com.bazarweb.bazarweb.repository.UserRepository;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public Cart findOrCreateCartByEmail(String email) {
        User user = userRepository.getUserByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));
        
        // Ищем корзину по userId, чтобы избежать дублирования
        Optional<Cart> cartOptional = cartRepository.findByUserId(user.getId());  // Предположим, что у вас есть метод findByUserId
        return cartOptional.orElseGet(() -> createCart(user));  // Если корзины нет - создаём новую
    }
    

    private Cart createCart(User user) {
        return cartRepository.save(new Cart(user));
    }

    public Cart addToCart(String email, int productId, int quantity) {
        Cart cart = findOrCreateCartByEmail(email);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));

        if (product.getProductStatus() == ProductStatus.IN_STOCK) {
            cart.update(product, quantity);
            return cartRepository.save(cart);
        } else {
            return cart; // Продукт недоступен, корзина остаётся без изменений
        }
    }

    public Cart clearCart(String email) {
        Cart cart = findOrCreateCartByEmail(email);

        // Удаляем все элементы из корзины
        cart.getItems().clear();

        // Обновляем общую стоимость
        cart.setTotalPrice(BigDecimal.ZERO);

        return cartRepository.save(cart);
    }

    

}
