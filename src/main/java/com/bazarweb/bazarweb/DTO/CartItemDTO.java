package com.bazarweb.bazarweb.DTO;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDTO {
    private int id;
    private String productName;
    private int quantity;
    private BigDecimal price;

    public CartItemDTO(int id, String productName, int quantity, BigDecimal price) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }
}