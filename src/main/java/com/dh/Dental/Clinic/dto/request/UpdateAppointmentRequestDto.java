package com.dh.Dental.Clinic.dto.request;

import com.dh.Dental.Clinic.entity.Patient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAppointmentRequestDto {
  private Integer id;
  private Integer patient_id;
  private Integer dentist_id;
  private String date;
}
