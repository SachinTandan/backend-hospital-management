package com.example.hospitalmanagement.DTO;

import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserLoginRequest {

    private String username;
    private String password;


}

