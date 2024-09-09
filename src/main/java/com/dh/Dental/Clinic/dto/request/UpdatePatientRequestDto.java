package com.dh.Dental.Clinic.dto.request;

import com.dh.Dental.Clinic.dto.response.AppointmentResponseDto;
import com.dh.Dental.Clinic.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePatientRequestDto {
  private Integer id;
  private String lastName;
  private String firstName;
  private String dni;
  private LocalDate admissionDate;
  private Address address;
  private Set<AppointmentResponseDto> appointment;
}
