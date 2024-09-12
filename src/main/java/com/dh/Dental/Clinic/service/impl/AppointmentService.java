package com.dh.Dental.Clinic.service.impl;

import com.dh.Dental.Clinic.dto.request.CreateAppointmentRequestDto;
import com.dh.Dental.Clinic.dto.request.UpdateAppointmentRequestDto;
import com.dh.Dental.Clinic.dto.response.AppointmentResponseDto;
import com.dh.Dental.Clinic.dto.response.DentistResponseDto;
import com.dh.Dental.Clinic.dto.response.PatientResponseDto;
import com.dh.Dental.Clinic.entity.Appointment;

import com.dh.Dental.Clinic.entity.Dentist;
import com.dh.Dental.Clinic.entity.Patient;
import com.dh.Dental.Clinic.exception.BadRequestException;

import com.dh.Dental.Clinic.exception.ResourceNotFoundException;


import com.dh.Dental.Clinic.repository.IAppointmentRepository;

import com.dh.Dental.Clinic.service.IAppointmentService;

import com.dh.Dental.Clinic.service.IDentistService;
import com.dh.Dental.Clinic.service.IPatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service

public class AppointmentService implements IAppointmentService {

    private final IAppointmentRepository appointmentRepository;
    private final IPatientService patientService;
    private final IDentistService dentistService;

    private static final Logger logger = LoggerFactory.getLogger(AppointmentService.class);


    public AppointmentService(IAppointmentRepository appointmentRepository,IPatientService patientService, IDentistService dentistService) {
        this.appointmentRepository = appointmentRepository;
        this.patientService = patientService;
        this.dentistService = dentistService;
    }

    @Override
    public AppointmentResponseDto saveAppointment(CreateAppointmentRequestDto createAppointmentRequestDto) {
        logger.info("Saving appointment for patient ID: {} and dentist ID: {}", createAppointmentRequestDto.getPatient_id(), createAppointmentRequestDto.getDentist_id());
        Optional<PatientResponseDto> patientResponseDto = patientService.findPatientById(createAppointmentRequestDto.getPatient_id());
        Optional<DentistResponseDto> dentistResponseDto = dentistService.findDentistById(createAppointmentRequestDto.getDentist_id());

        if (patientResponseDto.isEmpty() || dentistResponseDto.isEmpty()) {
            logger.warn("Failed to save appointment: Patient or Dentist not found");
            throw new BadRequestException("Patient or Dentist not found");

        }

        Patient patient = convertPatientDtoToEntity(patientResponseDto.get());
        Dentist dentist = convertDentistDtoToEntity(dentistResponseDto.get());

        Appointment appointment = new Appointment();
        Appointment appointmentBd = null;
        AppointmentResponseDto appointmentResponseDto = null;

        appointment.setPatient(patient);
        appointment.setDentist(dentist);
        appointment.setDate(LocalDate.parse(createAppointmentRequestDto.getDate()));

        appointmentBd = appointmentRepository.save(appointment);

        appointmentResponseDto = convertAppointmentToResponse(appointmentBd);
        logger.info("Successfully saved appointment with ID: {}", appointmentBd.getId());
        return appointmentResponseDto;
    }

    @Override
    public Optional<AppointmentResponseDto> findAppointmentById(Integer id) {
        logger.info("Finding appointment by ID: {}", id);
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Appointment not found with ID: {}", id);
                    return new ResourceNotFoundException("Appointment not found");});
        AppointmentResponseDto appointmentResponseDto = convertAppointmentToResponse(appointment);
        return Optional.of(appointmentResponseDto);
    }

    @Override
    public List<AppointmentResponseDto> findAllAppointments() {
        logger.info("Retrieving all appointments");
        List<Appointment> appointments = appointmentRepository.findAll();
        List<AppointmentResponseDto> responseDto = new ArrayList<>();
        for (Appointment appointment : appointments) {
            responseDto.add(convertAppointmentToResponse(appointment));
        }
        logger.info("Returned {} appointments", responseDto.size());
        return responseDto;
    }

    @Override
    public List<AppointmentResponseDto> findAppointmentsByPatient(Integer patientId) {
        logger.info("Finding appointments for patient ID: {}", patientId);
        List<Appointment> appointments = appointmentRepository.findByPatientId(patientId);
        List<AppointmentResponseDto> responseDto = new ArrayList<>();
        for (Appointment appointment : appointments) {
            responseDto.add(convertAppointmentToResponse(appointment));
        }
        logger.info("Found {} appointments for patient ID: {}", responseDto.size(), patientId);
        return responseDto;
    }
    @Override
    public void updateAppointment(UpdateAppointmentRequestDto updateAppointmentRequestDto) {
        logger.info("Updating appointment with ID: {}", updateAppointmentRequestDto.getId());
        Appointment appointment = appointmentRepository.findById(updateAppointmentRequestDto.getId())
                .orElseThrow(() ->{
                    logger.warn("Failed to update appointment: Appointment not found");
                    return new ResourceNotFoundException("Appointment not found");
                }
                );

        Optional<PatientResponseDto> patientResponseDto = patientService.findPatientById(updateAppointmentRequestDto.getPatient_id());
        Optional<DentistResponseDto> dentistResponseDto = dentistService.findDentistById(updateAppointmentRequestDto.getDentist_id());

        if (!patientResponseDto.isPresent() || !dentistResponseDto.isPresent()) {
            logger.warn("Failed to update appointment: Patient or Dentist not found");
            throw new BadRequestException("Patient or Dentist not found");

        }

        Patient patient = convertPatientDtoToEntity(patientResponseDto.get());
        Dentist dentist = convertDentistDtoToEntity(dentistResponseDto.get());

        appointment.setPatient(patient);

        appointment.setDentist(dentist);

        appointment.setDate(LocalDate.parse(updateAppointmentRequestDto.getDate()));

        appointmentRepository.save(appointment);
        logger.info("Successfully updated appointment with ID: {}", appointment.getId());
    }

    @Override
    public void deleteAppointment(Integer id) {
        logger.info("Attempting to delete appointment with ID: {}", id);
        if (!appointmentRepository.existsById(id)) {
            logger.warn("Failed to delete appointment: Appointment not found");
            throw new ResourceNotFoundException("Appointment not found");
        }
        appointmentRepository.deleteById(id);
        logger.info("Successfully deleted appointment with ID: {}", id);
    }

    @Override

    public List<AppointmentResponseDto> findAppointmentsByDate(LocalDate date) {

        List<Appointment> appointments = appointmentRepository.findByDate(date);

        List<AppointmentResponseDto> responseDto = new ArrayList<>();

        for (Appointment appointment : appointments) {

            responseDto.add(convertAppointmentToResponse(appointment));

        }

        return responseDto;

    }


    private AppointmentResponseDto convertAppointmentToResponse(Appointment appointment) {
        PatientResponseDto patientResponse = null;
        if (appointment.getPatient() != null) {
            patientResponse = new PatientResponseDto(
                appointment.getPatient().getId(),
                appointment.getPatient().getLastName(),
                appointment.getPatient().getFirstName(),
                appointment.getPatient().getDni(),
                appointment.getPatient().getAdmissionDate().toString()
            );
        }

        DentistResponseDto dentistResponse = null;
        if (appointment.getDentist() != null) {
            dentistResponse = new DentistResponseDto(
                appointment.getDentist().getId(),
                appointment.getDentist().getRegistrationNumber(),
                appointment.getDentist().getLastName(),
                appointment.getDentist().getFirstName()
            );
        }

        return new AppointmentResponseDto(
                appointment.getId(),
                patientResponse,
                dentistResponse,
                appointment.getDate().toString()
        );
    }

    private Patient convertPatientDtoToEntity(PatientResponseDto patientDto) {
        Patient patient = new Patient();
        patient.setId(patientDto.getId());
        patient.setLastName(patientDto.getLastName());
        patient.setFirstName(patientDto.getFirstName());
        patient.setDni(patientDto.getDni());
        patient.setAdmissionDate(LocalDate.parse(patientDto.getAdmissionDate()));
        return patient;
    }

    private Dentist convertDentistDtoToEntity(DentistResponseDto dentistDto) {
        Dentist dentist = new Dentist();
        dentist.setId(dentistDto.getId());
        dentist.setRegistrationNumber(dentistDto.getRegistrationNumber());
        dentist.setLastName(dentistDto.getLastName());
        dentist.setFirstName(dentistDto.getFirstName());
        return dentist;
    }

}
