package com.dh.Dental.Clinic.service;

import com.dh.Dental.Clinic.dto.request.CreateAppointmentRequestDto;
import com.dh.Dental.Clinic.dto.request.UpdateAppointmentRequestDto;
import com.dh.Dental.Clinic.dto.response.AppointmentResponseDto;
import com.dh.Dental.Clinic.entity.Appointment;

import java.util.List;

import java.util.Optional;

public interface IAppointmentService {

    AppointmentResponseDto saveAppointment(CreateAppointmentRequestDto createAppointmentRequestDto);

    Optional<AppointmentResponseDto> findAppointmentById(Integer id);

    List<AppointmentResponseDto> findAllAppointments();
    List<AppointmentResponseDto> findAppointmentsByPatient(Integer patientId);

    void updateAppointment(UpdateAppointmentRequestDto updateAppointmentRequestDto);

    void deleteAppointment(Integer id);

}