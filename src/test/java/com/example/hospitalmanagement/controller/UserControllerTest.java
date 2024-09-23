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
    // when the createUser method of UserService is called with requestDTO, do nothing
        doNothing().when(userService).createUser(requestDTO);
        // call the register method of UserController with requestDTO
        ResponseEntity<String> response = userController.register(requestDTO);
        // assert the response status and body
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Your account has been created successfully!", response.getBody());
        // verify the createUser method of UserService is called once with requestDTO
        verify(userService, times(1)).createUser(requestDTO);
    }

    @Test
    @DisplayName("Test user login success")
    void testLoginSuccess() {
        //Given : a UserLoginRequest object
        UserLoginRequest request = new UserLoginRequest();
        request.setUsername("testuser");
        request.setPassword("password");
// when the verify method of UserService is called with request, return "valid_token"
        when(userService.verify(request)).thenReturn("valid_token");
// Then: call the login method of UserController with request and assert the response
        String response = userController.login(request);
// assert the response
        assertEquals("valid_token", response);
// verify the verify method of UserService is called once with request
        verify(userService, times(1)).verify(request);
    }
    @Test
    @DisplayName("Test user login failure")
    void testLoginFailure() {
        // Given: a UserLoginRequest object
        UserLoginRequest request = new UserLoginRequest();
        request.setUsername("testuser");
        request.setPassword("wrong_password");
        // When: the verify method of UserService is called with request and returns "fail"
        when(userService.verify(request)).thenReturn("fail");
       // Then: call the login method of UserController with request and assert the response
        String response = userController.login(request);
        // assert the response
        assertEquals("fail", response);
        // verify the verify method of UserService is called once with request
        verify(userService, times(1)).verify(request);
    }
}