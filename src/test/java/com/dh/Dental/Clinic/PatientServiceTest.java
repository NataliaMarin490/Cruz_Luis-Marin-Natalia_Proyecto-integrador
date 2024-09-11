package com.dh.Dental.Clinic;

import com.dh.Dental.Clinic.dto.request.CreatePatientRequestDto;

import com.dh.Dental.Clinic.dto.response.PatientResponseDto;

import com.dh.Dental.Clinic.entity.Patient;

import com.dh.Dental.Clinic.repository.IPatientRepository;

import com.dh.Dental.Clinic.service.impl.PatientService;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class PatientServiceTest {

    @Autowired

    private IPatientRepository patientRepository;

    private PatientService patientService;

    @BeforeEach

    public void setUp() {

        patientService = new PatientService(patientRepository);

    }

    @Test
    public void testSavePatient() {
        CreatePatientRequestDto requestDto = new CreatePatientRequestDto(
                "Doe",
                "Jane",
                "12345678",
                LocalDate.now(),
                null,
                new HashSet<>()
        );
        PatientResponseDto response = patientService.savePatient(requestDto);

        assertThat(response).isNotNull();
        assertThat(response.getFirstName()).isEqualTo("Jane");
    }

    @Test

    public void testFindPatientById() {

        Patient patient = new Patient();

        patient.setLastName("Doe");

        patient.setFirstName("Jane");

        patient.setDni("12345678");

        patient.setAdmissionDate(LocalDate.now());

        patientRepository.save(patient);

        PatientResponseDto foundPatient = patientService.findPatientById(patient.getId()).orElse(null);

        assertThat(foundPatient).isNotNull();

        assertThat(foundPatient.getLastName()).isEqualTo("Doe");

    }

}