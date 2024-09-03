package com.dh.Dental.Clinic.dto.response;

import lombok.AllArgsConstructor;

import lombok.Getter;

import lombok.NoArgsConstructor;

import lombok.Setter;

@Getter

@Setter

@AllArgsConstructor

@NoArgsConstructor

public class PatientResponseDto {

    private Integer id;

    private String lastName;

    private String firstName;

    private String dni;

    private String admissionDate;

}
