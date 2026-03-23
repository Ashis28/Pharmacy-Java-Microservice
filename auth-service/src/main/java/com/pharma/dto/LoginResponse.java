package com.pharma.dto;

import com.pharma.model.Role;
import lombok.Getter;

import java.util.Set;

@Getter
public class LoginResponse {
    private final String token;
    private final String name;
    private final Set<Role> roles;

    public LoginResponse(String token, String name, Set<Role> roles) {
        this.token = token;
        this.name = name;
        this.roles = roles;
    }
}
