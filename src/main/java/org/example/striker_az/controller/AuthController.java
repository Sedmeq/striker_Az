package org.example.striker_az.controller;

import org.example.striker_az.dto.*;
import org.example.striker_az.entity.*;
import org.example.striker_az.service.*;
import org.example.striker_az.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            User user = userService.register(request);
            return ResponseEntity.ok("Qeydiyyat uğurlu! STRIKERAZ ID: " + user.getStrikerazId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            User user = userService.findByEmail(request.getEmail());

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return ResponseEntity.badRequest().body("Email və ya şifrə yanlışdır");
            }

            String token = jwtUtil.generateToken(user.getEmail());

            LoginResponse response = new LoginResponse();
            response.setToken(token);
            response.setStrikerazId(user.getStrikerazId());
            response.setName(user.getName());
            response.setEmail(user.getEmail());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Email və ya şifrə yanlışdır");
        }
    }
}