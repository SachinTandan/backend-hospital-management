package com.example.hospitalmanagement.Service;

import com.example.hospitalmanagement.DTO.PatientDTO;
import com.example.hospitalmanagement.Repo.PatientRepository;
import com.example.hospitalmanagement.model.PatientEntity;
import com.example.hospitalmanagement.utilities.PatientMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class PatientServiceTest {
    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test get patient by id")
    void testGetPatientById() {
        int patientId = 1;
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setId(patientId);
        patientEntity.setFirstName("John");
        patientEntity.setLastName("Doe");
        patientEntity.setDateOfBirth(LocalDate.of(1990, 1, 1));

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patientEntity));

        PatientDTO result = patientService.getPatientById(patientId);

        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals(LocalDate.of(1990, 1, 1), result.getDateOfBirth());
        verify(patientRepository, times(1)).findById(patientId);
    }

    @Test
    @DisplayName("Test get patient by id not found")
    void testGetPatientByIdNotFound() {
        int patientId = 1;

        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        PatientDTO result = patientService.getPatientById(patientId);

        assertNull(result);
        verify(patientRepository, times(1)).findById(patientId);
    }

    @Test
    @DisplayName("Test create patient")
    void testCreatePatient() {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setFirstName("Jane");
        patientDTO.setLastName("Smith");
        patientDTO.setDateOfBirth(LocalDate.of(1985, 5, 15));

        PatientEntity patientEntity = PatientMapper.toEntity(patientDTO);
        patientEntity.setId(1);

        when(patientRepository.save(any(PatientEntity.class))).thenReturn(patientEntity);

        PatientDTO result = patientService.createPatient(patientDTO);

        assertEquals("Jane", result.getFirstName());
        assertEquals("Smith", result.getLastName());
        assertEquals(LocalDate.of(1985, 5, 15), result.getDateOfBirth());
        verify(patientRepository, times(1)).save(any(PatientEntity.class));
    }
}