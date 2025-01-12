package com.bazarweb.bazarweb.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bazarweb.bazarweb.DTO.CategoryDTO;
import com.bazarweb.bazarweb.model.Category;
import com.bazarweb.bazarweb.service.CategoryService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {
    
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        if (categories.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categories);
    }

    @PostMapping("/admin/create")
    public ResponseEntity<String> postMethodName(@RequestBody CategoryDTO categoryDto) {
        // Преобразование CategoryDto в Category
        Category category = new Category();
        category.setName(categoryDto.getName());
        
        categoryService.categoryCreate(category);
        
        return ResponseEntity.ok("Category created successfully");
    }
    

    @PutMapping("path/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable int id, @RequestBody Category updatedCategory) {
        categoryService.updateCategory(id, updatedCategory);        
        return ResponseEntity.ok("Category updated successfully");
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable int id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Category deleted successfully");
    }
    

}
