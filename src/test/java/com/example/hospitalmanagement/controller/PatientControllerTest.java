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
        // Given: a patient id and a PatientDTO object
        int patientId = 1;
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setFirstName("John");
        patientDTO.setLastName("Doe");
        patientDTO.setDateOfBirth(LocalDate.of(1990, 1, 1));
        // When: the getPatientById method of PatientService is called
        when(patientService.getPatientById(patientId)).thenReturn(patientDTO);

        // Then: the getPatient method of PatientController is called and the response is asserted
        ResponseEntity<PatientDTO> response = patientController.getPatient(patientId);

        // Asserting the response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(patientDTO, response.getBody());

        // Verifying the getPatientById method of PatientService is called once
        verify(patientService, times(1)).getPatientById(patientId);
    }

    @Test
    @DisplayName("Test get patient not found")
    void testGetPatientNotFound() {
        // Given: a patient id

        int patientId = 1;
        // When: the getPatientById method of PatientService is called and returns null
        when(patientService.getPatientById(patientId)).thenReturn(null);
        // Then: the getPatient method of PatientController is called and the response is asserted
        ResponseEntity<PatientDTO> response = patientController.getPatient(patientId);
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        // Verifying the getPatientById method of PatientService is called once
        verify(patientService, times(1)).getPatientById(patientId);
    }

    @Test
    @DisplayName("Test create patient")
    void testCreatePatient() {
        // Given: a PatientDTO object
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setFirstName("Jane");
        patientDTO.setLastName("Smith");
        patientDTO.setDateOfBirth(LocalDate.of(1985, 5, 15));
        // When: the createPatient method of PatientService is called
        when(patientService.createPatient(patientDTO)).thenReturn(patientDTO);
        // Then: the createPatient method of PatientController is called and the response is asserted
        ResponseEntity<PatientDTO> response = patientController.createPatient(patientDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(patientDTO, response.getBody());
        verify(patientService, times(1)).createPatient(patientDTO);
    }
}