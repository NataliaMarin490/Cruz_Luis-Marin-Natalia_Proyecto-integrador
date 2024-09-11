
package com.dh.Dental.Clinic;

import com.dh.Dental.Clinic.dto.request.CreateDentistRequestDto;

import com.dh.Dental.Clinic.dto.response.DentistResponseDto;

import com.dh.Dental.Clinic.entity.Dentist;

import com.dh.Dental.Clinic.repository.IDentistRepository;

import com.dh.Dental.Clinic.service.impl.DentistService;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class DentistServiceTest {

    @Autowired

    private IDentistRepository dentistRepository;

    private DentistService dentistService;

    @BeforeEach

    public void setUp() {

        dentistService = new DentistService(dentistRepository);

    }

    @Test
    public void testSaveDentist() {
        CreateDentistRequestDto requestDto = new CreateDentistRequestDto(
                "1234",
                "Smith",
                "Alice",
                new HashSet<>() // Inicializa como conjunto vacío
        );

        DentistResponseDto response = dentistService.saveDentist(requestDto);

        assertThat(response).isNotNull();
        assertThat(response.getFirstName()).isEqualTo("Alice");
    }

    @Test

    public void testFindDentistById() {

        Dentist dentist = new Dentist();

        dentist.setRegistrationNumber("1234");

        dentist.setLastName("Doe");

        dentist.setFirstName("John");

        dentistRepository.save(dentist);

        DentistResponseDto foundDentist = dentistService.findDentistById(dentist.getId()).orElse(null);

        assertThat(foundDentist).isNotNull();

        assertThat(foundDentist.getLastName()).isEqualTo("Doe");

    }

}
