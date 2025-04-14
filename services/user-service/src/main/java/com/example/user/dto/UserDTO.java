package com.example.user.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String email;
    private String password;
    private String role;

    // Getters and Setters
}
