package com.example.hospitalmanagement.utilities;

import com.example.hospitalmanagement.DTO.PatientDTO;
import com.example.hospitalmanagement.model.PatientEntity;
public class PatientMapper {

    // Convert Entity to DTO
    public static PatientDTO toDTO(PatientEntity patientEntity) {
        PatientDTO dto = new PatientDTO();
        dto.setFirstName(patientEntity.getFirstName());
        dto.setLastName(patientEntity.getLastName());
        dto.setDateOfBirth(patientEntity.getDateOfBirth());
        return dto;
    }

    // Convert DTO to Entity
    public static PatientEntity toEntity(PatientDTO dto) {
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setFirstName(dto.getFirstName());
        patientEntity.setLastName(dto.getLastName());
        patientEntity.setDateOfBirth(dto.getDateOfBirth());
        return patientEntity;
    }
}


