package com.dh.Dental.Clinic.service.impl;

import com.dh.Dental.Clinic.dto.request.CreatePatientRequestDto;
import com.dh.Dental.Clinic.dto.request.UpdatePatientRequestDto;
import com.dh.Dental.Clinic.dto.response.AppointmentResponseDto;
import com.dh.Dental.Clinic.dto.response.DentistResponseDto;
import com.dh.Dental.Clinic.dto.response.PatientResponseDto;
import com.dh.Dental.Clinic.entity.Appointment;
import com.dh.Dental.Clinic.entity.Dentist;
import com.dh.Dental.Clinic.entity.Patient;

import com.dh.Dental.Clinic.repository.IPatientRepository;

import com.dh.Dental.Clinic.service.IPatientService;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service

public class PatientService implements IPatientService {

    private final IPatientRepository patientRepository;

    public PatientService(IPatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public PatientResponseDto savePatient(CreatePatientRequestDto createPatientRequestDto) {
        Patient patient = new Patient();
        PatientResponseDto patientResponseDto = null;

        patient.setFirstName(createPatientRequestDto.getFirstName());
        patient.setLastName(createPatientRequestDto.getLastName());
        patient.setDni(createPatientRequestDto.getDni());
        patient.setAddress(createPatientRequestDto.getAddress());
        patient.setAdmissionDate(createPatientRequestDto.getAdmissionDate());
        Set<Appointment> appointments = convertAppointmentDtoSetToEntitySet(createPatientRequestDto.getAppointment());
        patient.setAppointments(appointments);

        Patient patientDb = patientRepository.save(patient);
        patientResponseDto = convertPatientToResponse(patientDb);
        return patientResponseDto;
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
    public void updatePatient(UpdatePatientRequestDto updatePatientRequestDto) {
        if (patientRepository.existsById(updatePatientRequestDto.getId())) {
            Set<Appointment> appointments =
                convertAppointmentDtoSetToEntitySet(updatePatientRequestDto.getAppointment());

            Patient patient = new Patient(
                updatePatientRequestDto.getId(),
                updatePatientRequestDto.getLastName(), updatePatientRequestDto.getFirstName(),
                updatePatientRequestDto.getDni(), updatePatientRequestDto.getAdmissionDate(),
                updatePatientRequestDto.getAddress(), appointments);
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
