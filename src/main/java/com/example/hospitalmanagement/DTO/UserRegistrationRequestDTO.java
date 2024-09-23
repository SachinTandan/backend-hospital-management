package com.example.hospitalmanagement.DTO;

import com.example.hospitalmanagement.model.Role;
import lombok.*;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationRequestDTO {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String email;
    private Role role;
    // Getters and Setters

}

