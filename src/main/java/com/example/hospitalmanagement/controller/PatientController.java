package com.example.hospitalmanagement.controller;


import com.example.hospitalmanagement.DTO.PatientDTO;
import com.example.hospitalmanagement.Service.PatientService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

@Slf4j

@RestController
@RequestMapping("/api/patients")
public class PatientController {
    @Autowired
    private PatientService patientService;

//    private List<PatientEntity> patients = new ArrayList<>(List.of(new PatientEntity(1, "Sachin", "lastName", "date oof birth"), new PatientEntity(2, "Sachin2", "LastName2", "dob2 birth2")));


    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatient(@PathVariable int id) {
        PatientDTO patientDTO = patientService.getPatientById(id);
        return ResponseEntity.status(HttpStatus.OK).body(patientDTO);
    }

    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
        // return the csrf token as response
    }


    @PostMapping
    public ResponseEntity<PatientDTO> createPatient(@RequestBody PatientDTO patientDTO) {

      // id auto-generated
        log.info("Patient created");
        PatientDTO createdPatient = patientService.createPatient(patientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPatient);
    }

}
