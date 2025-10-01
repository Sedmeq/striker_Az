package org.example.striker_az.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestResponseDto {
    @NotNull
    private Long requestId;

    @NotNull
    private Boolean accepted;
}