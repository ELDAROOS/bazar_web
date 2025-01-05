package com.bazarweb.bazarweb.DTO;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDTO {
    private int id;
    private String email;
    private BigDecimal totalPrice;
    private List<CartItemDTO> items;

    public CartDTO(int id, String email, BigDecimal totalPrice, List<CartItemDTO> items) {
        this.id = id;
        this.email = email;
        this.totalPrice = totalPrice;
        this.items = items;
    }
}