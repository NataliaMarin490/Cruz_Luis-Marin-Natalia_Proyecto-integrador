package com.dh.Dental.Clinic.dto.response;

import com.dh.Dental.Clinic.entity.Address;
import lombok.AllArgsConstructor;

import lombok.Getter;

import lombok.NoArgsConstructor;

import lombok.Setter;

import java.util.Set;

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
