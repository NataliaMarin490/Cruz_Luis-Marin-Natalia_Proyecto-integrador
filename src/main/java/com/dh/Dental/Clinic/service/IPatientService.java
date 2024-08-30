package com.dh.Dental.Clinic.service;

import com.dh.Dental.Clinic.entity.Patient;

import java.util.List;

import java.util.Optional;

public interface IPatientService {

    Patient savePatient(Patient patient);

    Optional<Patient> findPatientById(Integer id);

    List<Patient> findAllPatients();

    void updatePatient(Patient patient);

    void deletePatient(Integer id);
}