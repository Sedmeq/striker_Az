package org.example.striker_az.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String strikerazId;
    private String name;
    private String email;
}