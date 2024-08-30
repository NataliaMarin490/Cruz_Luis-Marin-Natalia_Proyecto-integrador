package com.dh.Dental.Clinic.controller;

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
    public ResponseEntity<Patient> savePatient(@RequestBody Patient patient) {
        return ResponseEntity.ok(patientService.savePatient(patient));
    }

    @GetMapping ("/find/{id}")
    public ResponseEntity<Patient> findPatientById(@PathVariable Integer id) {
        Optional<Patient> patient = patientService.findPatientById(id);
        if (patient.isPresent()) {
            return ResponseEntity.ok(patient.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping ("/all")
    public ResponseEntity<List<Patient>> findAllPatients( ) {
        return ResponseEntity.ok(patientService.findAllPatients());
    }

    @PutMapping ("/update")
    public ResponseEntity<String> updatePatient(@RequestBody Patient patient) {

        Optional<Patient> existingPatient = patientService.findPatientById(patient.getId());
        if (existingPatient.isPresent()) {
            patientService.updatePatient(patient);
            String jsonResponse = "{\"message\": \"The patient was updated\"}";
            return ResponseEntity.ok(jsonResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable Integer id) {
        Optional<Patient> existingPatient = patientService.findPatientById(id);

        if (existingPatient.isPresent()) {
            patientService.deletePatient(id);
            String jsonResponse = "{\"message\": \"The patient was deleted\"}";
            return ResponseEntity.ok(jsonResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}