package com.nexus.screening.service;

import com.nexus.screening.dto.AuthResponse;
import com.nexus.screening.dto.LoginRequest;
import com.nexus.screening.dto.RegisterRequest;
import com.nexus.screening.model.User;
import com.nexus.screening.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service managing user registration, authentication, and credential validation.
 */
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new user account with hashed password.
     */
    public AuthResponse register(RegisterRequest request) {
        // 1. Verify email uniqueness
        if (userRepository.existsByEmail(request.getEmail())) {
            return AuthResponse.error("An account with this email already exists.");
        }

        // 2. Hash password securely
        String hashedPassword = passwordEncoder.encode(request.getPassword());

        // 3. Create and persist user entity
        User newUser = new User(
                request.getFullName(),
                request.getEmail().toLowerCase().trim(),
                hashedPassword,
                request.getRole()
        );

        User savedUser = userRepository.save(newUser);

        // 4. Return response
        return new AuthResponse(
                true,
                "Account created successfully.",
                "demo-jwt-token-" + savedUser.getId(),
                savedUser.getId(),
                savedUser.getFullName(),
                savedUser.getEmail(),
                savedUser.getRole()
        );
    }

    /**
     * Authenticates an existing user with email and password.
     */
    public AuthResponse login(LoginRequest request) {
        String cleanEmail = request.getEmail().toLowerCase().trim();

        // 1. Find user by email
        Optional<User> userOptional = userRepository.findByEmail(cleanEmail);
        if (userOptional.isEmpty()) {
            return AuthResponse.error("Invalid email or password.");
        }

        User user = userOptional.get();

        // 2. Verify password match
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return AuthResponse.error("Invalid email or password.");
        }

        // 3. Return authenticated response
        return new AuthResponse(
                true,
                "Login successful.",
                "demo-jwt-token-" + user.getId(),
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole()
        );
    }
}
