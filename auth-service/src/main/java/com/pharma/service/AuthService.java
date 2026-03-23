package com.pharma.service;

import com.pharma.dto.LoginRequest;
import com.pharma.dto.LoginResponse;
import com.pharma.dto.SignupRequest;
import com.pharma.model.Role;
import com.pharma.model.User;
import com.pharma.repository.UserRepository;
import com.pharma.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    public AuthService(UserRepository repo,PasswordEncoder encoder,JwtService jwtService) {
        this.repo = repo;
        this.encoder  =encoder;
        this.jwtService = jwtService;
    }

    public String signup(SignupRequest req) {
        if (repo.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        // Default to CUSTOMER if no roles provided
        Set<Role> roles = (req.getRoles() == null || req.getRoles().isEmpty())
                ? Set.of(Role.CUSTOMER)
                : req.getRoles().stream().map(Role::valueOf).collect(Collectors.toSet());

        User user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPassword(encoder.encode(req.getPassword()));
        user.setRoles(roles);
        repo.save(user);

        return "User registered successfully";
    }

    public LoginResponse login(LoginRequest req) {
        User user = repo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getEmail());
        return new LoginResponse(token, user.getName(), user.getRoles());
    }
}
