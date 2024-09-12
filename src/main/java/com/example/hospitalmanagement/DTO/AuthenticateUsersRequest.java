package com.example.hospitalmanagement.DTO;

import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthenticateUsersRequest {

    private String username;
    private String password;


}

