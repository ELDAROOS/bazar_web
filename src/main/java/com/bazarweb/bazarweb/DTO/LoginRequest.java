package com.bazarweb.bazarweb.DTO;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}