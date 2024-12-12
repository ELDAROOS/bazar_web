package com.bazarweb.bazarweb.repository;

import com.bazarweb.bazarweb.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
