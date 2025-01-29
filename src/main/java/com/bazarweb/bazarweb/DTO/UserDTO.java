package com.bazarweb.bazarweb.DTO;

import java.util.List;

import com.bazarweb.bazarweb.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private int id;
    private String username;
    private String email;
    private String phone;
    private String address;
    private UserRole role;
    private List<OrderDTO> orders;
    private boolean blocked;

    public UserDTO(int id, String username, String email, UserRole role, boolean blocked) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.blocked = blocked;
    }

}
