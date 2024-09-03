package com.dh.Dental.Clinic.service.impl;

import com.dh.Dental.Clinic.dto.response.AppointmentResponseDto;
import com.dh.Dental.Clinic.dto.response.DentistResponseDto;
import com.dh.Dental.Clinic.dto.response.PatientResponseDto;
import com.dh.Dental.Clinic.entity.Appointment;

import com.dh.Dental.Clinic.repository.IAppointmentRepository;

import com.dh.Dental.Clinic.service.IAppointmentService;

import com.dh.Dental.Clinic.service.IDentistService;
import com.dh.Dental.Clinic.service.IPatientService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

@Service

public class AppointmentService implements IAppointmentService {

    private final IAppointmentRepository appointmentRepository;
    private final IPatientService patientService;
    private final IDentistService dentistService;

    public AppointmentService(IAppointmentRepository appointmentRepository,IPatientService patientService, IDentistService dentistService) {
        this.appointmentRepository = appointmentRepository;
        this.patientService = patientService;
        this.dentistService = dentistService;
    }

    @Override
    public Appointment saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public Optional<AppointmentResponseDto> findAppointmentById(Integer id) {
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        return appointment.map(this::convertAppointmentToResponse);
    }

    @Override
    public List<AppointmentResponseDto> findAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        List<AppointmentResponseDto> responseDtos = new ArrayList<>();
        for (Appointment appointment : appointments) {
            responseDtos.add(convertAppointmentToResponse(appointment));
        }
        return responseDtos;
    }

    // TODO: Complete these 3 methods with the corresponding logic

    @Override
    public List<AppointmentResponseDto> findAppointmentsByPatient(Integer patientId) {
        List<Appointment> appointments = appointmentRepository.findByPatientId(patientId);
        List<AppointmentResponseDto> responseDtos = new ArrayList<>();
        for (Appointment appointment : appointments) {
            responseDtos.add(convertAppointmentToResponse(appointment));
        }
        return responseDtos;
    }
    @Override
    public void updateAppointment(Appointment appointment) {
        if (appointmentRepository.existsById(appointment.getId())) {
            appointmentRepository.save(appointment);
        } else {
            throw new RuntimeException("Appointment not found");
        }
    }

    @Override
    public void deleteAppointment(Integer id) {
        if (appointmentRepository.existsById(id)) {
            appointmentRepository.deleteById(id);
        } else {
            throw new RuntimeException("Appointment not found");
        }
    }


    private AppointmentResponseDto convertAppointmentToResponse(Appointment appointment) {
        PatientResponseDto patientResponse = new PatientResponseDto(
                appointment.getPatient().getId(),
                appointment.getPatient().getLastName(),
                appointment.getPatient().getFirstName(),
                appointment.getPatient().getDni(),
                appointment.getPatient().getAdmissionDate().toString()
        );

        DentistResponseDto dentistResponse = new DentistResponseDto(
                appointment.getDentist().getId(),
                appointment.getDentist().getRegistrationNumber(),
                appointment.getDentist().getLastName(),
                appointment.getDentist().getFirstName()
        );

        return new AppointmentResponseDto(
                appointment.getId(),
                patientResponse,
                dentistResponse,
                appointment.getDate().toString()
        );
    }

}
