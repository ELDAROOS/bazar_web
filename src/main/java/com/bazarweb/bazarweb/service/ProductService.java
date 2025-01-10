package com.bazarweb.bazarweb.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.bazarweb.bazarweb.model.Product;
import com.bazarweb.bazarweb.repository.ProductRepository;

@Service
public class ProductService {
    
    private final ProductRepository productRepository;
    
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    public Product save(Product product){
        return productRepository.save(product);
    }

    public Product productCreate(Product product){
        return save(product);
    }

    public Product getProductById(int id){
            return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product getByCode(int code){
        return productRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product getByName(String name){
        return productRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public void updateProduct(int id, Product updatedProduct) {
        var product = getProductById(id);
        product.setName(updatedProduct.getName());
        product.setPrice(updatedProduct.getPrice());
        product.setCategory(updatedProduct.getCategory());
        product.setQuantity(updatedProduct.getQuantity());
        productRepository.save(product);
    }

    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }
}
