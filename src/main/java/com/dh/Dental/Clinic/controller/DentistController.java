package com.dh.Dental.Clinic.controller;

import com.dh.Dental.Clinic.dto.request.CreateDentistRequestDto;
import com.dh.Dental.Clinic.dto.request.UpdateDentistRequestDto;
import com.dh.Dental.Clinic.dto.response.DentistResponseDto;
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
    public ResponseEntity<DentistResponseDto> saveDentist(@RequestBody CreateDentistRequestDto createDentistRequestDto) {
        return ResponseEntity.ok(dentistService.saveDentist(createDentistRequestDto));
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<DentistResponseDto> findDentistById(@PathVariable Integer id) {
        Optional<DentistResponseDto> dentist = dentistService.findDentistById(id);
        //return dentist.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        if (dentist.isPresent()) {
            return ResponseEntity.ok(dentist.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<DentistResponseDto>> findAllDentists() {
        return ResponseEntity.ok(dentistService.findAllDentists());
    }

    @PutMapping ("/update")
    public ResponseEntity<String> updateDentist(@RequestBody UpdateDentistRequestDto updateDentistRequestDto) {
        try {
            dentistService.updateDentist(updateDentistRequestDto);
            String jsonResponse = "{\"message\": \"The dentist was updated\"}";
            return ResponseEntity.ok(jsonResponse);
        } catch (RuntimeException e) {
            String jsonResponse = "{\"message\": \"Dentist not found\"}";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\": \"Dentist not found\"}");
        }
    }

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String> deleteDentist(@PathVariable Integer id) {
        try {
            dentistService.deleteDentist(id);
            String jsonResponse = "{\"message\": \"The dentist was updated\"}";
            return ResponseEntity.ok(jsonResponse);
        } catch (RuntimeException e) {
            String jsonResponse = "{\"message\": \"Dentist not found\"}";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonResponse);
        }
    }

}