package com.example.user.service;

import com.example.user.config.TenantContext;
import com.example.user.dto.UserDTO;
import com.example.user.entity.User;
import com.example.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(UserDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());
        user.setTenantId(TenantContext.getTenantId());

        return userRepository.save(user);
    }

    public List<User> getAllUsersForTenant() {
        return userRepository.findByTenantId(TenantContext.getTenantId());
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmailAndTenantId(email, TenantContext.getTenantId())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
