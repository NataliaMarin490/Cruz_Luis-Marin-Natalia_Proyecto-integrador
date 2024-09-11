package com.dh.Dental.Clinic.service.impl;

import com.dh.Dental.Clinic.dto.request.CreateDentistRequestDto;
import com.dh.Dental.Clinic.dto.request.UpdateDentistRequestDto;
import com.dh.Dental.Clinic.dto.response.AppointmentResponseDto;
import com.dh.Dental.Clinic.dto.response.DentistResponseDto;
import com.dh.Dental.Clinic.entity.Appointment;
import com.dh.Dental.Clinic.entity.Dentist;

import com.dh.Dental.Clinic.repository.IDentistRepository;

import com.dh.Dental.Clinic.service.IDentistService;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

import java.util.stream.Collectors;

@Service
public class DentistService implements IDentistService {

    private final IDentistRepository dentistRepository;

    public DentistService(IDentistRepository dentistRepository) {
        this.dentistRepository = dentistRepository;
    }

    @Override
    public DentistResponseDto saveDentist(CreateDentistRequestDto createDentistRequestDto) {
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
        return dentistResponseDto;
    }

    @Override
    public Optional<DentistResponseDto> findDentistById(Integer id) {
        Optional<Dentist> dentist = dentistRepository.findById(id);
        return dentist.map(this::convertDentistToResponse);
    }

    @Override
    public List<DentistResponseDto> findAllDentists() {
        List<Dentist> dentists = dentistRepository.findAll();
        List<DentistResponseDto> responseDto = new ArrayList<>();
        for (Dentist dentist : dentists) {
            responseDto.add(convertDentistToResponse(dentist));
        }
        return responseDto;
    }

    @Override
    public void updateDentist(UpdateDentistRequestDto updateDentistRequestDto) {
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

        } else {

            throw new RuntimeException("Dentist not found");

        }
    }

    @Override
    public void deleteDentist(Integer id) {
        if (dentistRepository.existsById(id)) {
            dentistRepository.deleteById(id);
        } else {
            throw new RuntimeException("Dentist not found");
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