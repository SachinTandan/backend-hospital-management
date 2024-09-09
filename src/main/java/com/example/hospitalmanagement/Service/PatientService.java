package com.example.hospitalmanagement.Service;

import com.example.hospitalmanagement.DTO.PatientDTO;
import com.example.hospitalmanagement.Repo.PatientRepository;
import com.example.hospitalmanagement.model.PatientEntity;
import com.example.hospitalmanagement.utilities.PatientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public PatientDTO getPatientById(int id) {
        Optional<PatientEntity> patientEntity = patientRepository.findById(id);
        return patientEntity.map(PatientMapper::toDTO).orElse(null);
    }

    public PatientDTO createPatient(PatientDTO patientDTO) {
        PatientEntity patientEntity = PatientMapper.toEntity(patientDTO);
        PatientEntity savedEntity = patientRepository.save(patientEntity); // id auto-generated
        return PatientMapper.toDTO(savedEntity);
    }
}
