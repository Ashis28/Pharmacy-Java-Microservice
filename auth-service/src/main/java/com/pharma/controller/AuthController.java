package com.pharmacy.auth.controller;

import com.pharmacy.auth.dto.LoginRequest;
import com.pharmacy.auth.dto.LoginResponse;
import com.pharmacy.auth.dto.SignupRequest;
import com.pharmacy.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequest req) {
        return service.signup(req);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest req) {
        return service.login(req);
    }
}
