package com.example.hospitalmanagement.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PatientDTO {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;

}
