package com.example.shop.controller;

import com.example.shop.model.Product;
import com.example.shop.repository.ProductRepository;
import com.example.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String getAllProducts(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products); // model.addAttribute работает корректно
        return "product-list"; // Возвращает шаблон "product-list.html"
    }

    @GetMapping("/new")
    public String showCreateForm(Model model){
        model.addAttribute("products", new Product());
        return "create-product";
    }

    @PostMapping
    public String createProduct(@ModelAttribute("product") Product product){
        productService.createProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model){
        Product product = productService.findById(id)
                .orElseThrow(() -> new RuntimeException("Такого продукта нет"));
        model.addAttribute("product", product);
        return "edit-product";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") Long id, @ModelAttribute("product") Product product){
        productService.updateProduct(id, product);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id){
        productService.deleteProduct(id);
        return "redirect:/products";
    }

}
