package com.dh.Dental.Clinic.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAppointmentRequestDto {
  private Integer patient_id;
  private Integer dentist_id;
  private String date;
}
