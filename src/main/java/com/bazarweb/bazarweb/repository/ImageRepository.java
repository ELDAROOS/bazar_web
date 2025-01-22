package com.bazarweb.bazarweb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bazarweb.bazarweb.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByName(String name);
    List<Image> findByProductId(Long productId);
    boolean existsByName(String name);
    void deleteByProductId(Long productId);
    List<Image> findByNameAndContentType(String name, String contentType);
}
