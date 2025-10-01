package org.example.striker_az.dto;

import lombok.Data;

@Data
public class ProfileUpdateRequest {
    private String name;
    private String phone;
    private Integer age;
    private String position;
}