package com.dh.Dental.Clinic;

import com.dh.Dental.Clinic.dto.request.CreateAppointmentRequestDto;

import com.dh.Dental.Clinic.dto.response.AppointmentResponseDto;

import com.dh.Dental.Clinic.entity.Appointment;

import com.dh.Dental.Clinic.entity.Dentist;

import com.dh.Dental.Clinic.entity.Patient;

import com.dh.Dental.Clinic.repository.IAppointmentRepository;

import com.dh.Dental.Clinic.repository.IDentistRepository;

import com.dh.Dental.Clinic.repository.IPatientRepository;

import com.dh.Dental.Clinic.service.IDentistService;

import com.dh.Dental.Clinic.service.IPatientService;

import com.dh.Dental.Clinic.service.impl.AppointmentService;
import com.dh.Dental.Clinic.service.impl.DentistService;
import com.dh.Dental.Clinic.service.impl.PatientService;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class AppointmentServiceTest {

    @Autowired

    private IAppointmentRepository appointmentRepository;

    @Autowired

    private IDentistRepository dentistRepository;

    @Autowired

    private IPatientRepository patientRepository;

    private AppointmentService appointmentService;

    @BeforeEach

    public void setUp() {

        appointmentService = new AppointmentService(appointmentRepository,

                new PatientService(patientRepository), new DentistService(dentistRepository));

// Inicializa un dentista y un paciente para las pruebas

        Dentist dentist = new Dentist();

        dentist.setRegistrationNumber("D123");

        dentist.setLastName("Smith");

        dentist.setFirstName("Alice");

        dentistRepository.save(dentist);

        Patient patient = new Patient();

        patient.setLastName("Doe");

        patient.setFirstName("John");

        patient.setDni("98765432");

        patient.setAdmissionDate(LocalDate.now());

        patientRepository.save(patient);

    }

    @Test

    public void testSaveAppointment() {

        CreateAppointmentRequestDto requestDto = new CreateAppointmentRequestDto(1, 1, "2023-10-01");

        AppointmentResponseDto appointmentResponseDto = appointmentService.saveAppointment(requestDto);

        assertThat(appointmentResponseDto).isNotNull();

        assertThat(appointmentResponseDto.getDate()).isEqualTo("2023-10-01");

    }

    @Test

    public void testFindAppointmentById() {

        Appointment appointment = new Appointment();

        appointment.setDate(LocalDate.parse("2023-10-01"));

        appointment.setPatient(patientRepository.findAll().get(0)); // Se asume que hay al menos un paciente

        appointment.setDentist(dentistRepository.findAll().get(0)); // Se asume que hay al menos un dentista

        appointmentRepository.save(appointment);

        AppointmentResponseDto foundAppointment = appointmentService.findAppointmentById(appointment.getId()).orElse(null);

        assertThat(foundAppointment).isNotNull();

        assertThat(foundAppointment.getId()).isEqualTo(appointment.getId());

    }

}