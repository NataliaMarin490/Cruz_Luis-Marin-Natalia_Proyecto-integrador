package com.dh.Dental.Clinic.service;

import com.dh.Dental.Clinic.dto.request.CreatePatientRequestDto;
import com.dh.Dental.Clinic.dto.request.UpdatePatientRequestDto;
import com.dh.Dental.Clinic.dto.response.PatientResponseDto;
import com.dh.Dental.Clinic.entity.Patient;

import java.util.List;

import java.util.Optional;

public interface IPatientService {

    PatientResponseDto savePatient(CreatePatientRequestDto createPatientRequestDto);

    Optional<PatientResponseDto> findPatientById(Integer id);

    List<PatientResponseDto> findAllPatients();

    void updatePatient(UpdatePatientRequestDto updatePatientRequestDto);

    void deletePatient(Integer id);
}