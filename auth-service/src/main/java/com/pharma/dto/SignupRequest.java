package com.pharmacy.auth.dto;

import lombok.Data;
import java.util.Set;

@Data
public class SignupRequest {
    private String name;
    private String email;
    private String password;
    private Set<String> roles;
}
