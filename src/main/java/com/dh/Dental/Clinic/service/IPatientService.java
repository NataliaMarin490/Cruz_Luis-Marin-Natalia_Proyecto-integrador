package com.dh.Dental.Clinic.service;

import com.dh.Dental.Clinic.dto.response.PatientResponseDto;
import com.dh.Dental.Clinic.entity.Patient;

import java.util.List;

import java.util.Optional;

public interface IPatientService {

    Patient savePatient(Patient patient);

    Optional<PatientResponseDto> findPatientById(Integer id);

    List<PatientResponseDto> findAllPatients();

    void updatePatient(Patient patient);

    void deletePatient(Integer id);
}