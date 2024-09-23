package com.example.hospitalmanagement.controller;

import com.example.hospitalmanagement.DTO.PatientDTO;
import com.example.hospitalmanagement.Service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PatientControllerTest {
    @Mock
    private PatientService patientService;
    @InjectMocks
    private PatientController patientController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test get patient")
    void testGetPatient() {
        int patientId = 1;
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setFirstName("John");
        patientDTO.setLastName("Doe");
        patientDTO.setDateOfBirth(LocalDate.of(1990, 1, 1));

        when(patientService.getPatientById(patientId)).thenReturn(patientDTO);

        ResponseEntity<PatientDTO> response = patientController.getPatient(patientId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(patientDTO, response.getBody());
        verify(patientService, times(1)).getPatientById(patientId);
    }

    @Test
    @DisplayName("Test get patient not found")
    void testGetPatientNotFound() {
        int patientId = 1;
        when(patientService.getPatientById(patientId)).thenReturn(null);
        ResponseEntity<PatientDTO> response = patientController.getPatient(patientId);
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(patientService, times(1)).getPatientById(patientId);
    }

    @Test
    @DisplayName("Test create patient")
    void testCreatePatient() {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setFirstName("Jane");
        patientDTO.setLastName("Smith");
        patientDTO.setDateOfBirth(LocalDate.of(1985, 5, 15));

        when(patientService.createPatient(patientDTO)).thenReturn(patientDTO);

        ResponseEntity<PatientDTO> response = patientController.createPatient(patientDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(patientDTO, response.getBody());
        verify(patientService, times(1)).createPatient(patientDTO);
    }
}