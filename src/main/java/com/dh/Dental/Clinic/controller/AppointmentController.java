package com.dh.Dental.Clinic.controller;

import com.dh.Dental.Clinic.dto.request.CreateAppointmentRequestDto;
import com.dh.Dental.Clinic.dto.request.UpdateAppointmentRequestDto;
import com.dh.Dental.Clinic.dto.response.AppointmentResponseDto;
import com.dh.Dental.Clinic.entity.Appointment;

import com.dh.Dental.Clinic.service.IAppointmentService;

import org.hibernate.sql.Update;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<AppointmentResponseDto> saveAppointment(@RequestBody CreateAppointmentRequestDto createAppointmentRequestDto) {
        return ResponseEntity.ok(appointmentService.saveAppointment(createAppointmentRequestDto));
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<AppointmentResponseDto> findAppointmentById(@PathVariable Integer id) {
        Optional<AppointmentResponseDto> appointment = appointmentService.findAppointmentById(id);
        //return appointment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        if (appointment.isPresent()) {
            return ResponseEntity.ok(appointment.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<AppointmentResponseDto>> findAllAppointments() {
        return ResponseEntity.ok(appointmentService.findAllAppointments());
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateAppointment(@RequestBody UpdateAppointmentRequestDto updateAppointmentRequestDto){
        try {
            appointmentService.updateAppointment(updateAppointmentRequestDto);
            String jsonResponse = "{\"message\": \"The appointment was updated\"}";
            return ResponseEntity.ok(jsonResponse);
        } catch (RuntimeException e) {
            String jsonResponse = "{\"message\": \"Appointment not found\"}";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonResponse);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable Integer id) {
        try {
            appointmentService.deleteAppointment(id);
            return ResponseEntity.ok("{\"message\": \"The appointment was deleted\"}");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\": \"Appointment not found\"}");
        }
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<AppointmentResponseDto>> findAppointmentsByPatient(@PathVariable Integer patientId) {
        return ResponseEntity.ok(appointmentService.findAppointmentsByPatient(patientId));
    }
}