package com.bazarweb.bazarweb.DTO;

import java.math.BigDecimal;
import java.util.List;

import com.bazarweb.bazarweb.enums.ProductStatus;
import com.bazarweb.bazarweb.model.Review;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductDetailsDto {
    private int id;
    private int code;
    private String name;
    private String description;
    private BigDecimal price;
    private int quantity;
    private ProductStatus productStatus;
    private String category;
    private String imagePath;
    private double averageRating;
    private List<Review> reviews;
}
