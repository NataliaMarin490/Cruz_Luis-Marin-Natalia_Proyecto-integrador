package com.dh.Dental.Clinic.service;

import com.dh.Dental.Clinic.dto.request.CreateDentistRequestDto;
import com.dh.Dental.Clinic.dto.request.UpdateDentistRequestDto;
import com.dh.Dental.Clinic.dto.response.DentistResponseDto;
import com.dh.Dental.Clinic.entity.Dentist;
import com.dh.Dental.Clinic.entity.Patient;

import java.util.List;

import java.util.Optional;

public interface IDentistService {

    DentistResponseDto saveDentist(CreateDentistRequestDto createDentistRequestDto);

    Optional<DentistResponseDto> findDentistById(Integer id);

    List<DentistResponseDto> findAllDentists();

    void updateDentist(UpdateDentistRequestDto updateDentistRequestDto);

    void deleteDentist(Integer id);
}