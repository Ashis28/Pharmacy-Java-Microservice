package com.pharmacy.auth.service;

import com.pharmacy.auth.dto.LoginRequest;
import com.pharmacy.auth.dto.LoginResponse;
import com.pharmacy.auth.dto.SignupRequest;
import com.pharmacy.auth.exception.InvalidCredentialsException;
import com.pharmacy.auth.exception.UserAlreadyExistsException;
import com.pharmacy.auth.exception.UserNotFoundException;
import com.pharmacy.auth.model.Role;
import com.pharmacy.auth.model.User;
import com.pharmacy.auth.repository.UserRepository;
import com.pharmacy.auth.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock UserRepository userRepo;
    @Mock PasswordEncoder encoder;
    @Mock JwtService jwtService;

    @InjectMocks AuthService authService;

    private SignupRequest signupRequest;
    private LoginRequest loginRequest;
    private User user;

    @BeforeEach
    void setUp() {
        signupRequest = new SignupRequest();
        signupRequest.setName("John");
        signupRequest.setEmail("john@example.com");
        signupRequest.setPassword("password123");

        loginRequest = new LoginRequest();
        loginRequest.setEmail("john@example.com");
        loginRequest.setPassword("password123");

        user = new User();
        user.setName("John");
        user.setEmail("john@example.com");
        user.setPassword("encodedPassword");
        user.setRoles(Set.of(Role.CUSTOMER));
    }

    @Test
    void signup_success() {
        when(userRepo.existsByEmail("john@example.com")).thenReturn(false);
        when(encoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepo.save(any(User.class))).thenReturn(user);

        String result = authService.signup(signupRequest);

        assertThat(result).isEqualTo("User registered successfully");
        verify(userRepo).save(any(User.class));
    }

    @Test
    void signup_throwsUserAlreadyExistsException_whenEmailTaken() {
        when(userRepo.existsByEmail("john@example.com")).thenReturn(true);

        assertThatThrownBy(() -> authService.signup(signupRequest))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessageContaining("john@example.com");

        verify(userRepo, never()).save(any());
    }

    @Test
    void login_success() {
        when(userRepo.findByEmail("john@example.com")).thenReturn(Optional.of(user));
        when(encoder.matches("password123", "encodedPassword")).thenReturn(true);
        when(jwtService.generateToken(anyString(), anySet())).thenReturn("jwt-token");

        LoginResponse response = authService.login(loginRequest);

        assertThat(response.getToken()).isEqualTo("jwt-token");
        assertThat(response.getName()).isEqualTo("John");
    }

    @Test
    void login_throwsUserNotFoundException_whenEmailNotFound() {
        when(userRepo.findByEmail("john@example.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authService.login(loginRequest))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("john@example.com");
    }

    @Test
    void login_throwsInvalidCredentialsException_whenPasswordWrong() {
        when(userRepo.findByEmail("john@example.com")).thenReturn(Optional.of(user));
        when(encoder.matches("password123", "encodedPassword")).thenReturn(false);

        assertThatThrownBy(() -> authService.login(loginRequest))
                .isInstanceOf(InvalidCredentialsException.class);
    }
}
