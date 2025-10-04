package org.example.striker_az.service;

import org.example.striker_az.dto.*;
import org.example.striker_az.entity.*;
import org.example.striker_az.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    @Transactional
    public Team createTeam(User owner, TeamCreateRequest request) {
        if (owner.getAge() == null || owner.getPosition() == null) {
            throw new RuntimeException("Komanda yaratmaq üçün yaş və mövqe məlumatları lazımdır");
        }

        if (teamRepository.existsByName(request.getName())) {
            throw new RuntimeException("Bu adda komanda artıq mövcuddur");
        }

        Team team = new Team();
        team.setName(request.getName());
        team.setOwner(owner);
        team.setMaxPlayers(request.getMaxPlayers());

        Team savedTeam = teamRepository.save(team);

        owner.setTeam(savedTeam);
        userRepository.save(owner);

        return savedTeam;
    }

    public List<TeamResponse> getAllTeams() {
        return teamRepository.findByOrderByCreatedAtDesc().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<TeamResponse> searchTeams(String name) {
        return teamRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

// TeamService.java faylına əlavə edilməli olan düzəliş

    private TeamResponse toResponse(Team team) {
        TeamResponse response = new TeamResponse();
        response.setId(team.getId());
        response.setName(team.getName());

        // Yoxlama əlavə olunur
        if (team.getOwner() != null) {
            response.setOwnerName(team.getOwner().getName());
            response.setOwnerStrikerazId(team.getOwner().getStrikerazId());
        } else {
            // Sahibi olmayan komandalar üçün standart dəyərlər
            response.setOwnerName("Sahib təyin edilməyib");
            response.setOwnerStrikerazId(null);
        }

        response.setPlayerCount(team.getPlayerCount());
        response.setMaxPlayers(team.getMaxPlayers());
        response.setAvailableSlots(team.getAvailableSlots());
        response.setAverageAge(team.getAverageAge());
        response.setCreatedAt(team.getCreatedAt()
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        return response;
    }
}
