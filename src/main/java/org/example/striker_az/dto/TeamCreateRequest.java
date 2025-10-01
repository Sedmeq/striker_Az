package org.example.striker_az.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamCreateRequest {
    @NotBlank(message = "Komanda adı tələb olunur")
    private String name;

    @Min(value = 5, message = "Minimum 5 oyunçu")
    @Max(value = 20, message = "Maksimum 20 oyunçu")
    private Integer maxPlayers = 11;
}