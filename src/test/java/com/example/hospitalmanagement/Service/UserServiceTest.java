package com.example.hospitalmanagement.Service;

import com.example.hospitalmanagement.DTO.UserLoginRequest;
import com.example.hospitalmanagement.DTO.UserRegistrationRequestDTO;
import com.example.hospitalmanagement.Repo.UserRepo;
import com.example.hospitalmanagement.model.Role;
import com.example.hospitalmanagement.model.UsersEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import org.springframework.security.core.Authentication;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Mock
    private UserRepo userRepo;

    @Mock
    private AuthenticationManager authManager;

    @Mock
    private JWTService jwtService;

    @Mock
    private BCryptPasswordEncoder encoder;
    @InjectMocks
    private UserService userService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test create user")
    void testCreateUser() {
        UserRegistrationRequestDTO requestDTO = new UserRegistrationRequestDTO();
        requestDTO.setUsername("testuser");
        requestDTO.setPassword("password");
        requestDTO.setFirstName("Test");
        requestDTO.setLastName("User");
        requestDTO.setDateOfBirth(LocalDate.of(1990, 1, 1));
        requestDTO.setEmail("test@example.com");
        requestDTO.setRole(Role.PATIENT);

        when(encoder.encode("password")).thenReturn("encodedPassword");

        userService.createUser(requestDTO);

        verify(userRepo, times(1)).save(any(UsersEntity.class));
    }
    @Test
    @DisplayName("Test verify user")
    void testVerifySuccess() {
        UserLoginRequest request = new UserLoginRequest();
        request.setUsername("testuser");
        request.setPassword("password");

        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);

        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);

        UsersEntity user = new UsersEntity();
        user.setUsername("testuser");
        user.setRole(Role.PATIENT);

        when(userRepo.findByUsername("testuser")).thenReturn(user);
        when(jwtService.generateToken("testuser", Role.PATIENT)).thenReturn("valid_token");

        String result = userService.verify(request);

        assertEquals("valid_token", result);
    }
    @Test
    @DisplayName("Test verify user failure")
    void testVerifyFailure() {
        UserLoginRequest request = new UserLoginRequest();
        request.setUsername("testuser");
        request.setPassword("wrong_password");

        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(false);

        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);

        String result = userService.verify(request);

        assertEquals("fail", result);
    }
}