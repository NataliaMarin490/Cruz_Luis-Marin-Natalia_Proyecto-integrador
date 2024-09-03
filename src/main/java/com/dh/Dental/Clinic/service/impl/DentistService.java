package com.dh.Dental.Clinic.service.impl;

import com.dh.Dental.Clinic.dto.response.DentistResponseDto;
import com.dh.Dental.Clinic.entity.Dentist;

import com.dh.Dental.Clinic.entity.Patient;
import com.dh.Dental.Clinic.repository.IDentistRepository;

import com.dh.Dental.Clinic.service.IDentistService;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

@Service
public class DentistService implements IDentistService {

    private final IDentistRepository dentistRepository;

    public DentistService(IDentistRepository dentistRepository) {
        this.dentistRepository = dentistRepository;
    }

    @Override
    public Dentist saveDentist(Dentist dentist) {
        return dentistRepository.save(dentist);
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
    public void updateDentist(Dentist dentist) {
        if (dentistRepository.existsById(dentist.getId())) {
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
}