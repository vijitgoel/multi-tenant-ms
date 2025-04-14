package com.example.user.controller;

import com.example.user.config.TenantContext;
import com.example.user.dto.AuthResponse;
import com.example.user.dto.LoginRequest;
import com.example.user.entity.User;
import com.example.user.repository.UserRepository;
import com.example.user.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request,
                                   @RequestHeader("X-Tenant-ID") String tenantId) {

        // Set tenant in context for this thread
        TenantContext.setTenantId(tenantId);

        // Authenticate credentials
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // Fetch user based on tenant
        User user = userRepository.findByEmailAndTenantId(request.getEmail(), tenantId)
                .orElseThrow(() -> new RuntimeException("User not found for tenant"));

        // Generate JWT
        String token = jwtUtil.generateToken(user);

        return ResponseEntity.ok(new AuthResponse(token));
    }
}
