package com.example.user.service;

import com.example.user.config.TenantContext;
import com.example.user.dto.LoginRequest;
import com.example.user.dto.AuthResponse;
import com.example.user.entity.User;
import com.example.user.repository.UserRepository;
import com.example.user.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public AuthService(AuthenticationManager authenticationManager,
                       JwtUtil jwtUtil,
                       UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    public AuthResponse login(LoginRequest request) {
        // Authenticate user
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // Get user and tenant info
        User user = userRepository.findByEmailAndTenantId(
                request.getEmail(), TenantContext.getTenantId()
        ).orElseThrow(() -> new RuntimeException("Invalid email or tenant"));

        // Generate JWT
        String token = jwtUtil.generateToken(user);

        return new AuthResponse(token);
    }
}
