package com.dh.Dental.Clinic.service.impl;

import com.dh.Dental.Clinic.entity.Dentist;

import com.dh.Dental.Clinic.entity.Patient;
import com.dh.Dental.Clinic.repository.IDentistRepository;

import com.dh.Dental.Clinic.service.IDentistService;

import org.springframework.stereotype.Service;

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
    public Optional<Dentist> findDentistById(Integer id) {
        return dentistRepository.findById(id);
    }

    @Override
    public List<Dentist> findAllDentists() {
        return dentistRepository.findAll();
    }

    @Override
    public void updateDentist(Dentist dentist) {
        dentistRepository.save(dentist);
    }

    @Override
    public void deleteDentist(Integer id) {
        dentistRepository.deleteById(id);
    }
}