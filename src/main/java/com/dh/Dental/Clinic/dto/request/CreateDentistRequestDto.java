package com.dh.Dental.Clinic.dto.request;

import com.dh.Dental.Clinic.dto.response.AppointmentResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateDentistRequestDto {
  private String registrationNumber;
  private String lastName;
  private String firstName;
  private Set<AppointmentResponseDto> appointment;
}
