package com.example.hospitalmanagement.controller;


import com.example.hospitalmanagement.DTO.AuthenticateUsersRequest;
import com.example.hospitalmanagement.DTO.UserRegistrationRequestDTO;
import com.example.hospitalmanagement.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegistrationRequestDTO user) {
        log.info("Register new user " + user);
        userService.createUser(user);
        log.info("User registered successfully ");
        return ResponseEntity.status(HttpStatus.CREATED).body("Your account has been created successfully!");

    }

    @PostMapping("/login")
    public String login(@RequestBody AuthenticateUsersRequest userLoginRequest) {
        log.info("Logging user {} ", userLoginRequest.getUsername());
        return userService.verify(userLoginRequest);
    }
//    @GetMapping("/{id}")
//    public ResponseEntity<AuthenticateUsersRequest> getUser(@PathVariable int id) {
//        AuthenticateUsersRequest userResponseDTO = userService.getUserById(id);
//        if (userResponseDTO != null) {
//            return ResponseEntity.ok(userResponseDTO);
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//    }
}
