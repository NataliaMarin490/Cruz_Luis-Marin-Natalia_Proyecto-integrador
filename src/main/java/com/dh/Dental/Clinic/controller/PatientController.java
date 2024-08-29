package com.dh.Dental.Clinic.controller;

import com.dh.Dental.Clinic.entity.Patient;

import com.dh.Dental.Clinic.service.IPatientService;

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

    @PostMapping("/save")

    public ResponseEntity<Patient> savePatient(@RequestBody Patient patient) {

        return ResponseEntity.ok(patientService.savePatient(patient));

    }

    @GetMapping("/find/{id}")

    public ResponseEntity<Patient> findPatientById(@PathVariable Integer id) {

        Optional<Patient> patient = patientService.findPatientById(id);

        if (patient.isPresent()) {

            return ResponseEntity.ok(patient.get());

        } else {

            return ResponseEntity.notFound().build();

        }

    }

    @GetMapping("/all")

    public ResponseEntity<List<Patient>> findAllPatients() {

        return ResponseEntity.ok(patientService.findAllPatients());

    }

}