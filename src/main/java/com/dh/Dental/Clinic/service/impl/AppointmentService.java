package com.dh.Dental.Clinic.service.impl;

import com.dh.Dental.Clinic.dto.request.CreateAppointmentRequestDto;
import com.dh.Dental.Clinic.dto.request.UpdateAppointmentRequestDto;
import com.dh.Dental.Clinic.dto.response.AppointmentResponseDto;
import com.dh.Dental.Clinic.dto.response.DentistResponseDto;
import com.dh.Dental.Clinic.dto.response.PatientResponseDto;
import com.dh.Dental.Clinic.entity.Appointment;

import com.dh.Dental.Clinic.entity.Dentist;
import com.dh.Dental.Clinic.entity.Patient;
import com.dh.Dental.Clinic.repository.IAppointmentRepository;

import com.dh.Dental.Clinic.service.IAppointmentService;

import com.dh.Dental.Clinic.service.IDentistService;
import com.dh.Dental.Clinic.service.IPatientService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service

public class AppointmentService implements IAppointmentService {

    private final IAppointmentRepository appointmentRepository;
    private final IPatientService patientService;
    private final IDentistService dentistService;

    public AppointmentService(IAppointmentRepository appointmentRepository,IPatientService patientService, IDentistService dentistService) {
        this.appointmentRepository = appointmentRepository;
        this.patientService = patientService;
        this.dentistService = dentistService;
    }

    @Override
    public AppointmentResponseDto saveAppointment(CreateAppointmentRequestDto createAppointmentRequestDto) {
        Optional<PatientResponseDto> patientResponseDto = patientService.findPatientById(createAppointmentRequestDto.getPatient_id());
        Optional<DentistResponseDto> dentistResponseDto = dentistService.findDentistById(createAppointmentRequestDto.getDentist_id());

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
        return appointmentResponseDto;
    }

    @Override
    public Optional<AppointmentResponseDto> findAppointmentById(Integer id) {
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        AppointmentResponseDto appointmentResponseDto = convertAppointmentToResponse(appointment.get());
        return Optional.of(appointmentResponseDto);
    }

    @Override
    public List<AppointmentResponseDto> findAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        List<AppointmentResponseDto> responseDto = new ArrayList<>();
        for (Appointment appointment : appointments) {
            responseDto.add(convertAppointmentToResponse(appointment));
        }
        return responseDto;
    }

    @Override
    public List<AppointmentResponseDto> findAppointmentsByPatient(Integer patientId) {
        List<Appointment> appointments = appointmentRepository.findByPatientId(patientId);
        List<AppointmentResponseDto> responseDto = new ArrayList<>();
        for (Appointment appointment : appointments) {
            responseDto.add(convertAppointmentToResponse(appointment));
        }
        return responseDto;
    }
    @Override
    public void updateAppointment(UpdateAppointmentRequestDto updateAppointmentRequestDto) {
        Optional<PatientResponseDto> patientResponseDto = patientService.findPatientById(updateAppointmentRequestDto.getPatient_id());
        Optional<DentistResponseDto> dentistResponseDto = dentistService.findDentistById(updateAppointmentRequestDto.getDentist_id());

        Patient patient = convertPatientDtoToEntity(patientResponseDto.get());
        Dentist dentist = convertDentistDtoToEntity(dentistResponseDto.get());

        if(patientResponseDto.isPresent() && dentistResponseDto.isPresent()){
            Appointment appointment = new Appointment(
                updateAppointmentRequestDto.getId(),
                patient, dentist, LocalDate.parse(updateAppointmentRequestDto.getDate())
            );
            appointmentRepository.save(appointment);
        }
    }

    @Override
    public void deleteAppointment(Integer id) {
        if (appointmentRepository.existsById(id)) {
            appointmentRepository.deleteById(id);
        } else {
            throw new RuntimeException("Appointment not found");
        }
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
