package com.pharma.dto;

import com.pharma.model.Role;
import java.util.Set;

public class LoginResponse {

    private String token;
    private String name;
    private Set<Role> roles;

    public LoginResponse(String token, String name, Set<Role> roles) {
        this.token = token;
        this.name = name;
        this.roles = roles;
    }

    public String getToken() { return token; }
    public String getName() { return name; }
    public Set<Role> getRoles() { return roles; }
}
