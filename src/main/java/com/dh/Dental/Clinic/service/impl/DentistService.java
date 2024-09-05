package com.dh.Dental.Clinic.service.impl;

import com.dh.Dental.Clinic.dto.request.CreateDentistRequestDto;
import com.dh.Dental.Clinic.dto.request.UpdateDentistRequestDto;
import com.dh.Dental.Clinic.dto.response.AppointmentResponseDto;
import com.dh.Dental.Clinic.dto.response.DentistResponseDto;
import com.dh.Dental.Clinic.entity.Appointment;
import com.dh.Dental.Clinic.entity.Dentist;

import com.dh.Dental.Clinic.entity.Patient;
import com.dh.Dental.Clinic.repository.IDentistRepository;

import com.dh.Dental.Clinic.service.IDentistService;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import java.util.Set;
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
        Set<Appointment> appointments = convertAppointmentDtoSetToEntitySet(createDentistRequestDto.getAppointment());
        dentist.setAppointments(appointments);

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
        List<DentistResponseDto> responseDtos = new ArrayList<>();
        for (Dentist dentist : dentists) {
            responseDtos.add(convertDentistToResponse(dentist));
        }
        return responseDtos;
    }

    @Override
    public void updateDentist(UpdateDentistRequestDto updateDentistRequestDto) {
        if (dentistRepository.existsById(updateDentistRequestDto.getId())) {
            Set<Appointment> appointments =
                convertAppointmentDtoSetToEntitySet(updateDentistRequestDto.getAppointment());

            Dentist dentist = new Dentist(
                updateDentistRequestDto.getId(),
                updateDentistRequestDto.getRegistrationNumber(), updateDentistRequestDto.getLastName(),
                updateDentistRequestDto.getFirstName(), appointments);
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