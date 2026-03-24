package com.pharma.service;

import com.pharma.dto.LoginRequest;
import com.pharma.dto.LoginResponse;
import com.pharma.dto.SignupRequest;
import com.pharma.model.Address;
import com.pharma.model.Role;
import com.pharma.model.User;
import com.pharma.repository.AddressRepository;
import com.pharma.repository.UserRepository;
import com.pharma.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final UserRepository userRepo;
    private final AddressRepository addressRepo;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepo, AddressRepository addressRepo,
                       PasswordEncoder encoder, JwtService jwtService) {
        this.userRepo = userRepo;
        this.addressRepo = addressRepo;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public String signup(SignupRequest req) {
        if (userRepo.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        Set<Role> roles = (req.getRoles() == null || req.getRoles().isEmpty())
                ? Set.of(Role.CUSTOMER)
                : req.getRoles().stream().map(Role::valueOf).collect(Collectors.toSet());

        // 1. Save user first so we have the generated ID
        User user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPassword(encoder.encode(req.getPassword()));
        user.setRoles(roles);
        User savedUser = userRepo.save(user);

        // 2. Save address linked to the saved user
        Address address = new Address();
        address.setUser(savedUser);
        address.setStreet(req.getAddress().getStreet());
        address.setCity(req.getAddress().getCity());
        address.setState(req.getAddress().getState());
        address.setPinCode(req.getAddress().getPinCode());
        address.setDefault(true); // first address is always default
        addressRepo.save(address);

        return "User registered successfully";
    }

    public LoginResponse login(LoginRequest req) {
        User user = userRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getEmail());
        return new LoginResponse(token, user.getName(), user.getRoles());
    }
}
