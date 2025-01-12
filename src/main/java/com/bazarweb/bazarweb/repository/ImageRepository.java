package com.bazarweb.bazarweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bazarweb.bazarweb.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer>{
    
}
