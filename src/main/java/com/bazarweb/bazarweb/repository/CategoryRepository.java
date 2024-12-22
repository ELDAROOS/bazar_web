package com.bazarweb.bazarweb.repository;

import com.bazarweb.bazarweb.model.Category;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findById(int id);
    Optional<Category> findByName(String name);
}
