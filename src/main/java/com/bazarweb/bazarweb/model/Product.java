package com.bazarweb.bazarweb.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String description;
    private BigDecimal price;
    private String image;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
