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
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Bu email artıq qeydiyyatdan keçib");
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Şifrələr uyğun gəlmir");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStrikerazId(generateStrikerazId());

        return userRepository.save(user);
    }

    private String generateStrikerazId() {
        Long maxId = userRepository.findMaxId();
        long nextId = (maxId == null) ? 1 : maxId + 1;
        return String.format("STR%03d", nextId);
    }

    public User updateProfile(User user, ProfileUpdateRequest request) {
        if (request.getName() != null) user.setName(request.getName());
        if (request.getPhone() != null) user.setPhone(request.getPhone());
        if (request.getAge() != null) user.setAge(request.getAge());
        if (request.getPosition() != null) user.setPosition(request.getPosition());

        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("İstifadəçi tapılmadı"));
    }

    public User findByStrikerazId(String id) {
        return userRepository.findByStrikerazId(id)
                .orElseThrow(() -> new RuntimeException("İstifadəçi tapılmadı"));
    }
}