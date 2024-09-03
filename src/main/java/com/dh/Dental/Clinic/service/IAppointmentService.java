package com.dh.Dental.Clinic.service;

import com.dh.Dental.Clinic.dto.response.AppointmentResponseDto;
import com.dh.Dental.Clinic.entity.Appointment;

import java.util.List;

import java.util.Optional;

public interface IAppointmentService {

    Appointment saveAppointment(Appointment appointment);

    Optional<AppointmentResponseDto> findAppointmentById(Integer id);

    List<AppointmentResponseDto> findAllAppointments();
    List<AppointmentResponseDto> findAppointmentsByPatient(Integer patientId);

    void updateAppointment(Appointment appointment);

    void deleteAppointment(Integer id);

}