package com.dh.Dental.Clinic.service;

import com.dh.Dental.Clinic.entity.Appointment;

import java.util.List;

import java.util.Optional;

public interface IAppointmentService {

    Appointment saveAppointment(Appointment appointment);

    Optional<Appointment> findAppointmentById(Integer id);

    List<Appointment> findAllAppointments();

}