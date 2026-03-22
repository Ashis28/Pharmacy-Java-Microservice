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
    private final JwtService jwt;

    public String signup(SignupRequest req) {
        if (repo.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        Set<Role> roles = req.getRoles()
                .stream()
                .map(Role::valueOf)
                .collect(Collectors.toSet());
        User u = new User();
        u.setName(req.getName());
        u.setEmail(req.getEmail());
        u.setPassword(encoder.encode(req.getPassword()));
        u.setRoles(roles);
        repo.save(u);
        return "User created";
    }

    public LoginResponse login(LoginRequest req) {
        User u = repo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!encoder.matches(req.getPassword(), u.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        String token = jwt.generateToken(u.getEmail());
        return new LoginResponse(token);
    }
}
