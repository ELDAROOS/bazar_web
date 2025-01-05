package com.bazarweb.bazarweb.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddToCartRequest {
    private String email;
    private int productId;
    private int quantity;
}