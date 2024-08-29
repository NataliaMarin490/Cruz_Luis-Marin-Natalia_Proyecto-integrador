package com.dh.Dental.Clinic.controller;

import com.dh.Dental.Clinic.entity.Dentist;

import com.dh.Dental.Clinic.service.IDentistService;

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

}