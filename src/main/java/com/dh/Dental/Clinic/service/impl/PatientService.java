package com.dh.Dental.Clinic.service.impl;

import com.dh.Dental.Clinic.dto.request.CreatePatientRequestDto;
import com.dh.Dental.Clinic.dto.request.UpdatePatientRequestDto;
import com.dh.Dental.Clinic.dto.response.AppointmentResponseDto;
import com.dh.Dental.Clinic.dto.response.DentistResponseDto;
import com.dh.Dental.Clinic.dto.response.PatientResponseDto;
import com.dh.Dental.Clinic.entity.Appointment;
import com.dh.Dental.Clinic.entity.Dentist;
import com.dh.Dental.Clinic.entity.Patient;

import com.dh.Dental.Clinic.exception.BadRequestException;

import com.dh.Dental.Clinic.exception.ResourceNotFoundException;

import com.dh.Dental.Clinic.repository.IPatientRepository;

import com.dh.Dental.Clinic.service.IPatientService;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

import java.util.stream.Collectors;

@Service

public class PatientService implements IPatientService {

    private final IPatientRepository patientRepository;
    private static final Logger logger = LoggerFactory.getLogger(PatientService.class);

    public PatientService(IPatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public PatientResponseDto savePatient(CreatePatientRequestDto createPatientRequestDto) {
        logger.info("Saving patient with DNI: {}", createPatientRequestDto.getDni());
        Patient patient = new Patient();
        PatientResponseDto patientResponseDto = null;

        patient.setFirstName(createPatientRequestDto.getFirstName());
        patient.setLastName(createPatientRequestDto.getLastName());
        patient.setDni(createPatientRequestDto.getDni());
        patient.setAddress(createPatientRequestDto.getAddress());
        patient.setAdmissionDate(createPatientRequestDto.getAdmissionDate());
        if (createPatientRequestDto.getAppointment() != null) {

            Set<Appointment> appointments = convertAppointmentDtoSetToEntitySet(createPatientRequestDto.getAppointment());

            patient.setAppointments(appointments);

        }

        Patient patientDb = patientRepository.save(patient);
        patientResponseDto = convertPatientToResponse(patientDb);
        logger.info("Successfully saved patient with ID: {}", patientDb.getId());
        return patientResponseDto;
    }

    @Override
    public Optional<PatientResponseDto> findPatientById(Integer id) {
        logger.info("Finding patient by ID: {}", id);
        Optional<Patient> patient = patientRepository.findById(id);

        if (patient.isPresent()) {

            return Optional.of(convertPatientToResponse(patient.get()));

        } else {
            logger.warn("Patient not found with ID: {}", id);
            return Optional.empty();

        }
    }

    @Override
    public List<PatientResponseDto> findAllPatients() {
        logger.info("Retrieving all patients");
        List<Patient> patients = patientRepository.findAll();

        List<PatientResponseDto> responseDtos = new ArrayList<>();

        for (Patient patient : patients) {

            responseDtos.add(convertPatientToResponse(patient));

        }
        logger.info("Returned {} patients", responseDtos.size());
        return responseDtos;
    }

    @Override
    public void updatePatient(UpdatePatientRequestDto updatePatientRequestDto) {
        logger.info("Updating patient with ID: {}", updatePatientRequestDto.getId());
        if (patientRepository.existsById(updatePatientRequestDto.getId())) {

            Patient patient = new Patient();

            patient.setId(updatePatientRequestDto.getId());

            patient.setLastName(updatePatientRequestDto.getLastName());

            patient.setFirstName(updatePatientRequestDto.getFirstName());

            patient.setDni(updatePatientRequestDto.getDni());

            patient.setAdmissionDate(updatePatientRequestDto.getAdmissionDate());

            patient.setAddress(updatePatientRequestDto.getAddress());


            if (updatePatientRequestDto.getAppointment() != null) {

                Set<Appointment> appointments = convertAppointmentDtoSetToEntitySet(updatePatientRequestDto.getAppointment());

                patient.setAppointments(appointments);

            }

            patientRepository.save(patient);
            logger.info("Successfully updated patient with ID: {}", patient.getId());

        } else {
            logger.warn("Failed to update patient: Patient not found");
            throw new ResourceNotFoundException("Patient not found");

        }
    }

    @Override
    public void deletePatient(Integer id) {
        logger.info("Attempting to delete patient with ID: {}", id);
        if (patientRepository.existsById(id)) {

            patientRepository.deleteById(id);
            logger.info("Successfully deleted patient with ID: {}", id);

        } else {
            logger.warn("Failed to delete patient: Patient not found");
            throw new ResourceNotFoundException("Patient not found");

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

    private Set<Appointment> convertAppointmentDtoSetToEntitySet(Set<AppointmentResponseDto> appointmentResponseDtoSet) {
        return appointmentResponseDtoSet.stream()
                .map(this::convertAppointmentDtoToEntity)
                .collect(Collectors.toSet());
    }

    private Appointment convertAppointmentDtoToEntity(AppointmentResponseDto appointmentResponseDto) {
        Appointment appointment = new Appointment();
        appointment.setId(appointmentResponseDto.getId());
        appointment.setDate(LocalDate.parse(appointmentResponseDto.getDate()));

        return appointment;
    }


}
