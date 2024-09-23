package com.example.hospitalmanagement.controller;

import com.example.hospitalmanagement.DTO.UserLoginRequest;
import com.example.hospitalmanagement.DTO.UserRegistrationRequestDTO;
import com.example.hospitalmanagement.Service.UserService;
import com.example.hospitalmanagement.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {
    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test user registration")
    void testRegister() {
        UserRegistrationRequestDTO requestDTO = new UserRegistrationRequestDTO();
        requestDTO.setUsername("testuser");
        requestDTO.setPassword("password");
        requestDTO.setFirstName("Test");
        requestDTO.setLastName("User");
        requestDTO.setDateOfBirth(LocalDate.of(1990, 1, 1));
        requestDTO.setEmail("test@example.com");
        requestDTO.setRole(Role.PATIENT);

        doNothing().when(userService).createUser(requestDTO);

        ResponseEntity<String> response = userController.register(requestDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Your account has been created successfully!", response.getBody());
        verify(userService, times(1)).createUser(requestDTO);
    }

    @Test
    @DisplayName("Test user login success")
    void testLoginSuccess() {
        UserLoginRequest request = new UserLoginRequest();
        request.setUsername("testuser");
        request.setPassword("password");

        when(userService.verify(request)).thenReturn("valid_token");

        String response = userController.login(request);

        assertEquals("valid_token", response);
        verify(userService, times(1)).verify(request);
    }
    @Test
    @DisplayName("Test user login failure")
    void testLoginFailure() {
        UserLoginRequest request = new UserLoginRequest();
        request.setUsername("testuser");
        request.setPassword("wrong_password");

        when(userService.verify(request)).thenReturn("fail");
        String response = userController.login(request);
        assertEquals("fail", response);
        verify(userService, times(1)).verify(request);
    }
}