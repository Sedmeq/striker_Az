package org.example.striker_az.controller;


import org.example.striker_az.dto.*;
import org.example.striker_az.entity.*;
import org.example.striker_az.service.*;
import org.example.striker_az.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/requests")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RequestController {
    private final RequestService requestService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> sendRequest(@Valid @RequestBody JoinRequestDto dto) {
        try {
            User user = getCurrentUser();
            JoinRequest request = requestService.createRequest(user, dto);
            return ResponseEntity.ok("Müraciət göndərildi");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/respond")
    public ResponseEntity<?> respondToRequest(@Valid @RequestBody RequestResponseDto dto) {
        try {
            User user = getCurrentUser();
            requestService.respondToRequest(user, dto);
            return ResponseEntity.ok("Cavab göndərildi");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/team")
    public ResponseEntity<List<JoinRequest>> getTeamRequests() {
        User user = getCurrentUser();
        return ResponseEntity.ok(requestService.getTeamRequests(user));
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findByEmail(email);
    }
}
