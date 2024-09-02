package com.dh.Dental.Clinic.service;

import com.dh.Dental.Clinic.entity.Dentist;
import com.dh.Dental.Clinic.entity.Patient;

import java.util.List;

import java.util.Optional;

public interface IDentistService {

    Dentist saveDentist(Dentist dentist);

    Optional<Dentist> findDentistById(Integer id);

    List<Dentist> findAllDentists();

    void updateDentist(Dentist dentist);

    void deleteDentist(Integer id);
}