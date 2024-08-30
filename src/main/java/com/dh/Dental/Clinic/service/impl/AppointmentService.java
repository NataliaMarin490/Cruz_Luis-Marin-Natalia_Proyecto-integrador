package com.dh.Dental.Clinic.service.impl;

import com.dh.Dental.Clinic.entity.Appointment;

import com.dh.Dental.Clinic.repository.IAppointmentRepository;

import com.dh.Dental.Clinic.service.IAppointmentService;

import org.springframework.stereotype.Service;

import java.util.List;

import java.util.Optional;

@Service

public class AppointmentService implements IAppointmentService {

    private final IAppointmentRepository appointmentRepository;

    public AppointmentService(IAppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public Appointment saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public Optional<Appointment> findAppointmentById(Integer id) {
        return appointmentRepository.findById(id);
    }

    @Override
    public List<Appointment> findAllAppointments() {
        return appointmentRepository.findAll();
    }

    // TODO: Complete these 3 methods with the corresponding logic
    @Override
    public void updateAppointment(Appointment appointment) {
    }

    @Override
    public void deleteAppointment(Integer id) {
    }

    @Override
    public Optional<Appointment> findAppointmentsByPatient(String patient) {
        return Optional.empty();
    }

}
