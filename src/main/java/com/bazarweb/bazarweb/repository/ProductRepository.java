package com.bazarweb.bazarweb.repository;

import com.bazarweb.bazarweb.model.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findById(int id);
    Optional<Product> findByName(String name);
    Optional<Product> findByCode(int code);
    boolean existsById(int id);
    boolean existsByCode(int code);
}