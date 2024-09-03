package com.dh.Dental.Clinic.repository;

import com.dh.Dental.Clinic.entity.Appointment;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;


@Repository

public interface IAppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findByPatientId(Integer patientId);
}