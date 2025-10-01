package org.example.striker_az.dto;

import lombok.Data;

@Data
public class TeamResponse {
    private Long id;
    private String name;
    private String ownerName;
    private String ownerStrikerazId;
    private Integer playerCount;
    private Integer maxPlayers;
    private Integer availableSlots;
    private Double averageAge;
    private String createdAt;
}