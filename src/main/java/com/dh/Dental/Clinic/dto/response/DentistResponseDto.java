package com.dh.Dental.Clinic.dto.response;

import lombok.AllArgsConstructor;

import lombok.Getter;

import lombok.NoArgsConstructor;

import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DentistResponseDto {

    private Integer id;
    private String registrationNumber;
    private String lastName;
    private String firstName;
}