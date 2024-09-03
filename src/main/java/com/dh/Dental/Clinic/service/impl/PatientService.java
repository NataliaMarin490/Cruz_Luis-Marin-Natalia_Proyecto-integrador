package com.dh.Dental.Clinic.service.impl;

import com.dh.Dental.Clinic.dto.response.PatientResponseDto;
import com.dh.Dental.Clinic.entity.Patient;

import com.dh.Dental.Clinic.repository.IPatientRepository;

import com.dh.Dental.Clinic.service.IPatientService;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

@Service

public class PatientService implements IPatientService {

    private final IPatientRepository patientRepository;

    public PatientService(IPatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Optional<PatientResponseDto> findPatientById(Integer id) {
        Optional<Patient> patient = patientRepository.findById(id);

        if (patient.isPresent()) {

            return Optional.of(convertPatientToResponse(patient.get()));

        } else {

            return Optional.empty();

        }
    }

    @Override
    public List<PatientResponseDto> findAllPatients() {
        List<Patient> patients = patientRepository.findAll();

        List<PatientResponseDto> responseDtos = new ArrayList<>();

        for (Patient patient : patients) {

            responseDtos.add(convertPatientToResponse(patient));

        }

        return responseDtos;
    }

    @Override
    public void updatePatient(Patient patient) {
        if (patientRepository.existsById(patient.getId())) {

            patientRepository.save(patient);

        } else {

            throw new RuntimeException("Patient not found");

        }
    }

    @Override
    public void deletePatient(Integer id) {
        if (patientRepository.existsById(id)) {

            patientRepository.deleteById(id);

        } else {

            throw new RuntimeException("Patient not found");

        }
    }

    private PatientResponseDto convertPatientToResponse(Patient patient) {

        return new PatientResponseDto(

                patient.getId(),

                patient.getLastName(),

                patient.getFirstName(),

                patient.getDni(),

                patient.getAdmissionDate().toString()

        );

    }


}
