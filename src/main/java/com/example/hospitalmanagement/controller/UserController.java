package com.example.hospitalmanagement.controller;


import com.example.hospitalmanagement.DTO.UserRequestDTO;
import com.example.hospitalmanagement.DTO.UserResponseDTO;
import com.example.hospitalmanagement.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRequestDTO user) {
        log.info("Register new user " + user);
        UserResponseDTO registeredUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);

    }

    @PostMapping("/login")
    public String login(@RequestBody UserRequestDTO user) {
        log.info("Logging user {} ", user);
        return userService.verify(user);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable int id) {
        UserResponseDTO userResponseDTO = userService.getUserById(id);
        if (userResponseDTO != null) {
            return ResponseEntity.ok(userResponseDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
