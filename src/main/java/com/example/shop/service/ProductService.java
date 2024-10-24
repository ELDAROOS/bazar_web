package com.example.shop.service;

import com.example.shop.model.Category;
import com.example.shop.model.Product;
import com.example.shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public Product createProduct(Product product){
        return productRepository.save(product);
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id){
        return productRepository.findById(id);
    }

    public List<Product> findByName(String name){
        return productRepository.findByNameContaining(name);
    }

    public List<Product> findByCategory(Category category){
        return productRepository.findByCategory(category);
    }

    public Product updateProduct(Long id, Product productDetails){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Продукт не найден"));

        product.setName(productDetails.getName());
        product.setCategory(productDetails.getCategory());
        product.setDescription(productDetails.getDescription());
        product.setStockQuantity(productDetails.getStockQuantity());
        product.setPrice(productDetails.getPrice());

        return productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }
}
