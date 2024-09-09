package com.example.hospitalmanagement.Repo;

import com.example.hospitalmanagement.model.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<PatientEntity,Integer> {
}
