package com.dh.Dental.Clinic.controller;

import com.dh.Dental.Clinic.entity.Appointment;

import com.dh.Dental.Clinic.service.IAppointmentService;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.Optional;

@RestController

@RequestMapping("/appointments")

public class AppointmentController {

    private final IAppointmentService appointmentService;

    public AppointmentController(IAppointmentService appointmentService) {

        this.appointmentService = appointmentService;

    }

    @PostMapping("/save")

    public ResponseEntity<Appointment> saveAppointment(@RequestBody Appointment appointment) {

        return ResponseEntity.ok(appointmentService.saveAppointment(appointment));

    }

    @GetMapping("/find/{id}")

    public ResponseEntity<Appointment> findAppointmentById(@PathVariable Integer id) {

        Optional<Appointment> appointment = appointmentService.findAppointmentById(id);

        if (appointment.isPresent()) {

            return ResponseEntity.ok(appointment.get());

        } else {

            return ResponseEntity.notFound().build();

        }

    }

    @GetMapping("/all")

    public ResponseEntity<List<Appointment>> findAllAppointments() {

        return ResponseEntity.ok(appointmentService.findAllAppointments());

    }

    // PUT - UPDATE
    // GET - FINDBYLASTNAME

}