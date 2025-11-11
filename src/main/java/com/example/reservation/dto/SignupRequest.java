package com.example.reservation.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String username;
    private String email;
    private String password;
    private String role; // "USER" یا "ADMIN" یا "MANAGER"
}
