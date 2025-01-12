package com.bazarweb.bazarweb.repository;

import com.bazarweb.bazarweb.model.Review;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByProductId(int productId); // Получение отзывов по товару
}
