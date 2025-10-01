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
public class RequestService {
    private final JoinRequestRepository requestRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    @Transactional
    public JoinRequest createRequest(User player, JoinRequestDto dto) {
        Team team = teamRepository.findById(dto.getTeamId())
                .orElseThrow(() -> new RuntimeException("Komanda tapılmadı"));

        if (team.getAvailableSlots() <= 0) {
            throw new RuntimeException("Komandada boş yer yoxdur");
        }

        if (requestRepository.existsByPlayerAndTeamAndStatus(
                player, team, RequestStatus.PENDING)) {
            throw new RuntimeException("Bu komandaya artıq müraciət göndərmisiniz");
        }

        JoinRequest request = new JoinRequest();
        request.setPlayer(player);
        request.setTeam(team);
        request.setMessage(dto.getMessage());

        JoinRequest saved = requestRepository.save(request);

        notificationService.notifyTeamOwner(team.getOwner(), player, team);

        return saved;
    }

    @Transactional
    public void respondToRequest(User owner, RequestResponseDto dto) {
        JoinRequest request = requestRepository.findById(dto.getRequestId())
                .orElseThrow(() -> new RuntimeException("Müraciət tapılmadı"));

        if (!request.getTeam().getOwner().getId().equals(owner.getId())) {
            throw new RuntimeException("Bu müraciətə cavab vermək hüququnuz yoxdur");
        }

        request.setStatus(dto.getAccepted() ?
                RequestStatus.ACCEPTED : RequestStatus.REJECTED);
        request.setRespondedAt(LocalDateTime.now());

        requestRepository.save(request);

        if (dto.getAccepted()) {
            User player = request.getPlayer();
            player.setTeam(request.getTeam());
            userRepository.save(player);

            notificationService.notifyPlayerAccepted(player, request.getTeam());
        } else {
            notificationService.notifyPlayerRejected(
                    request.getPlayer(), request.getTeam());
        }
    }

    public List<JoinRequest> getTeamRequests(User owner) {
        return requestRepository.findByTeam_Owner(owner);
    }
}