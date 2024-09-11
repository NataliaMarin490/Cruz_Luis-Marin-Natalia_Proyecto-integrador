package com.dh.Dental.Clinic.repository;

import com.dh.Dental.Clinic.entity.Appointment;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository

public interface IAppointmentRepository extends JpaRepository<Appointment, Integer> {

    List<Appointment> findByPatientId(Integer patientId);

    @Query("SELECT a FROM Appointment a WHERE a.date = :date")

    List<Appointment> findByDate(@Param("date") LocalDate date);

}