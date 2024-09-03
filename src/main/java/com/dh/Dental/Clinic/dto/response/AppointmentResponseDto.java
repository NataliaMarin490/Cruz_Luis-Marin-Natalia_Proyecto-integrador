package com.dh.Dental.Clinic.dto.response;

import lombok.AllArgsConstructor;

import lombok.Getter;

import lombok.NoArgsConstructor;

import lombok.Setter;

@Getter

@Setter

@AllArgsConstructor

@NoArgsConstructor

public class AppointmentResponseDto {

    private Integer id;

    private PatientResponseDto patientResponseDto;

    private DentistResponseDto dentistResponseDto;

    private String date;

}
