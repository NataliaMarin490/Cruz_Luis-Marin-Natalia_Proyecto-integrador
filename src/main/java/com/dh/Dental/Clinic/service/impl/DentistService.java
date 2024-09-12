package com.dh.Dental.Clinic.service.impl;

import com.dh.Dental.Clinic.dto.request.CreateDentistRequestDto;
import com.dh.Dental.Clinic.dto.request.UpdateDentistRequestDto;
import com.dh.Dental.Clinic.dto.response.AppointmentResponseDto;
import com.dh.Dental.Clinic.dto.response.DentistResponseDto;
import com.dh.Dental.Clinic.entity.Appointment;
import com.dh.Dental.Clinic.entity.Dentist;

import com.dh.Dental.Clinic.exception.ResourceNotFoundException;

import com.dh.Dental.Clinic.repository.IDentistRepository;

import com.dh.Dental.Clinic.service.IDentistService;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

import java.util.stream.Collectors;

@Service
public class DentistService implements IDentistService {

    private final IDentistRepository dentistRepository;
    private static final Logger logger = LoggerFactory.getLogger(DentistService.class);

    public DentistService(IDentistRepository dentistRepository) {
        this.dentistRepository = dentistRepository;
    }

    @Override
    public DentistResponseDto saveDentist(CreateDentistRequestDto createDentistRequestDto) {
        logger.info("Saving dentist with registration number: {}", createDentistRequestDto.getRegistrationNumber());
        Dentist dentist = new Dentist();
        DentistResponseDto dentistResponseDto = null;

        dentist.setRegistrationNumber(createDentistRequestDto.getRegistrationNumber());
        dentist.setFirstName(createDentistRequestDto.getFirstName());
        dentist.setLastName(createDentistRequestDto.getLastName());
        if (createDentistRequestDto.getAppointment() != null) {

            Set<Appointment> appointments = convertAppointmentDtoSetToEntitySet(createDentistRequestDto.getAppointment());

            dentist.setAppointments(appointments);

        }

        Dentist dentistBd = dentistRepository.save(dentist);
        dentistResponseDto = convertDentistToResponse(dentistBd);
        logger.info("Successfully saved dentist with ID: {}", dentistBd.getId());
        return dentistResponseDto;
    }

    @Override
    public Optional<DentistResponseDto> findDentistById(Integer id) {
        logger.info("Finding dentist by ID: {}", id);
        Optional<Dentist> dentist = dentistRepository.findById(id);
        return dentist.map(this::convertDentistToResponse).or(() -> {
            logger.warn("Dentist not found with ID: {}", id);
            return Optional.empty();
        });
    }

    @Override
    public List<DentistResponseDto> findAllDentists() {
        logger.info("Retrieving all dentists");
        List<Dentist> dentists = dentistRepository.findAll();
        List<DentistResponseDto> responseDto = new ArrayList<>();
        for (Dentist dentist : dentists) {
            responseDto.add(convertDentistToResponse(dentist));
        }
        logger.info("Returned {} dentists", responseDto.size());
        return responseDto;
    }

    @Override
    public void updateDentist(UpdateDentistRequestDto updateDentistRequestDto) {
        logger.info("Updating dentist with ID: {}", updateDentistRequestDto.getId());

        if (dentistRepository.existsById(updateDentistRequestDto.getId())) {
            Dentist dentist = new Dentist();

            dentist.setId(updateDentistRequestDto.getId());

            dentist.setRegistrationNumber(updateDentistRequestDto.getRegistrationNumber());

            dentist.setLastName(updateDentistRequestDto.getLastName());

            dentist.setFirstName(updateDentistRequestDto.getFirstName());

            if (updateDentistRequestDto.getAppointment() != null) {

                Set<Appointment> appointments = convertAppointmentDtoSetToEntitySet(updateDentistRequestDto.getAppointment());
                dentist.setAppointments(appointments);

            }

            dentistRepository.save(dentist);
            logger.info("Successfully updated dentist with ID: {}", dentist.getId());

        } else {
            logger.warn("Failed to update dentist: Dentist not found");
            throw new ResourceNotFoundException("Dentist not found");
        }
    }

    @Override
    public void deleteDentist(Integer id) {
        logger.info("Attempting to delete dentist with ID: {}", id);
        if (dentistRepository.existsById(id)) {
            dentistRepository.deleteById(id);
            logger.info("Successfully deleted dentist with ID: {}", id);
        } else {
            logger.warn("Failed to delete dentist: Dentist not found");
            throw new ResourceNotFoundException("Dentist not found");
        }
    }

    @Override

    public Optional<DentistResponseDto> findDentistByRegistrationNumber(String registrationNumber) {

        Optional<Dentist> dentist = dentistRepository.findByRegistrationNumber(registrationNumber);

        return dentist.map(this::convertDentistToResponse);

    }


    private DentistResponseDto convertDentistToResponse(Dentist dentist) {
        return new DentistResponseDto(
                dentist.getId(),
                dentist.getRegistrationNumber(),
                dentist.getLastName(),
                dentist.getFirstName()
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