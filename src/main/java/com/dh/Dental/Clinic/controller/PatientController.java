package com.dh.Dental.Clinic.controller;

import com.dh.Dental.Clinic.dto.request.CreatePatientRequestDto;
import com.dh.Dental.Clinic.dto.request.UpdatePatientRequestDto;
import com.dh.Dental.Clinic.dto.response.PatientResponseDto;
import com.dh.Dental.Clinic.entity.Patient;

import com.dh.Dental.Clinic.service.IPatientService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.Optional;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final IPatientService patientService;

    public PatientController(IPatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping ("/save")
    public ResponseEntity<PatientResponseDto> savePatient(@RequestBody CreatePatientRequestDto createPatientRequestDto) {
        return ResponseEntity.ok(patientService.savePatient(createPatientRequestDto));
    }

    @GetMapping ("/find/{id}")
    public ResponseEntity<PatientResponseDto> findPatientById(@PathVariable Integer id) {
        Optional<PatientResponseDto> patient = patientService.findPatientById(id);
        // return patient.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        if (patient.isPresent()) {
            return ResponseEntity.ok(patient.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }
    @GetMapping ("/all")
    public ResponseEntity<List<PatientResponseDto>> findAllPatients( ) {
        return ResponseEntity.ok(patientService.findAllPatients());
    }

    @PutMapping ("/update")
    public ResponseEntity<String> updatePatient(@RequestBody UpdatePatientRequestDto updatePatientRequestDto) {
        try {
            patientService.updatePatient(updatePatientRequestDto);
            String jsonResponse = "{\"message\": \"The patient was updated\"}";
            return ResponseEntity.ok(jsonResponse);
        } catch (RuntimeException e) {
            String jsonResponse = "{\"message\": \"Patient not found\"}";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonResponse);
        }
    }

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable Integer id) {
        try {
            patientService.deletePatient(id);
            String jsonResponse = "{\"message\": \"The patient was deleted\"}";
            return ResponseEntity.ok(jsonResponse);
        } catch (RuntimeException e) {
            String jsonResponse = "{\"message\": \"Patient not found\"}";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonResponse);
        }

    }
}