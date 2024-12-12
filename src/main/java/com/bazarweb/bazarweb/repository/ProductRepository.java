package com.bazarweb.bazarweb.repository;

import com.bazarweb.bazarweb.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
