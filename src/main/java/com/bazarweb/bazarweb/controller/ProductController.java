package com.bazarweb.bazarweb.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bazarweb.bazarweb.DTO.ProductDetailsDto;
import com.bazarweb.bazarweb.model.Product;
import com.bazarweb.bazarweb.model.Review;
import com.bazarweb.bazarweb.service.ProductService;
import com.bazarweb.bazarweb.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ReviewService reviewService;

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailsDto> getProductById(@PathVariable int id) {
        Product product = productService.getProductById(id);
        List<Review> reviews = reviewService.getReviewsByProductId(id);
        double averageRating = reviewService.calculateAverageRating(id);

        ProductDetailsDto productDetails = ProductDetailsDto.builder()
            .id(product.getId())
            .code(product.getCode())
            .name(product.getName())
            .description(product.getDescription())
            .price(product.getPrice())
            .quantity(product.getQuantity())
            .productStatus(product.getProductStatus())
            .category(product.getCategory().getName())
            //.imagePath(product.getImagePath())
            .averageRating(averageRating)
            .reviews(reviews)
            .build();

        return ResponseEntity.ok(productDetails);
    }

    

    @PostMapping("/admin/create")
    public ResponseEntity<String> createProduct(@RequestBody Product product) {
        productService.productCreate(product);
        return ResponseEntity.ok("Product created successfully");
    }

    @PutMapping("/admin/edit/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestBody Product updatedProduct) {
        productService.updateProduct(id, updatedProduct);
        return ResponseEntity.ok("Product updated successfully");
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    
}