package com.dh.Dental.Clinic.controller;

import com.dh.Dental.Clinic.entity.Dentist;

import com.dh.Dental.Clinic.service.IDentistService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.Optional;

@RestController

@RequestMapping("/dentists")

public class DentistController {

    private final IDentistService dentistService;

    public DentistController(IDentistService dentistService) {
        this.dentistService = dentistService;
    }

    @PostMapping("/save")
    public ResponseEntity<Dentist> saveDentist(@RequestBody Dentist dentist) {
        return ResponseEntity.ok(dentistService.saveDentist(dentist));
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Dentist> findDentistById(@PathVariable Integer id) {
        Optional<Dentist> dentist = dentistService.findDentistById(id);
        if (dentist.isPresent()) {
            return ResponseEntity.ok(dentist.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Dentist>> findAllDentists() {
        return ResponseEntity.ok(dentistService.findAllDentists());
    }

    @PutMapping ("/update")
    public ResponseEntity<String> updateDentist(@RequestBody Dentist dentist) {

        Optional<Dentist> existingDentist = dentistService.findDentistById(dentist.getId());
        if (existingDentist.isPresent()) {
            dentistService.updateDentist(dentist);
            String jsonResponse = "{\"message\": \"The dentist was updated\"}";
            return ResponseEntity.ok(jsonResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String> deleteDentist(@PathVariable Integer id) {
        Optional<Dentist> existingDentist = dentistService.findDentistById(id);

        if (existingDentist.isPresent()) {
            dentistService.deleteDentist(id);
            String jsonResponse = "{\"message\": \"The dentist was deleted\"}";
            return ResponseEntity.ok(jsonResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}