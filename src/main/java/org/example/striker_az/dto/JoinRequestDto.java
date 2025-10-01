package org.example.striker_az.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JoinRequestDto {
    @NotNull
    private Long teamId;
    private String message;
}