package com.example.hospitalmanagement.Service;

import com.example.hospitalmanagement.DTO.PatientDTO;
import com.example.hospitalmanagement.Repo.PatientRepository;
import com.example.hospitalmanagement.exceptionHandling.ResourceNotFoundException;
import com.example.hospitalmanagement.model.PatientEntity;
import com.example.hospitalmanagement.utilities.PatientMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Slf4j
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public PatientDTO getPatientById(int id) {
        Optional<PatientEntity> patientEntity = patientRepository.findById(id);
        return patientEntity.map(PatientMapper::toDTO).orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
    }

    public PatientDTO createPatient(PatientDTO patientDTO) {
        PatientEntity patientEntity = PatientMapper.toEntity(patientDTO);
        PatientEntity savedEntity = patientRepository.save(patientEntity);
        log.info("Patient created in database " + savedEntity);
        return PatientMapper.toDTO(savedEntity);
    }
}
