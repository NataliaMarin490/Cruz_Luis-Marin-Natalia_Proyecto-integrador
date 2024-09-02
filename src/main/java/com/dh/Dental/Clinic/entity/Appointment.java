package com.dh.Dental.Clinic.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;

import lombok.Getter;

import lombok.NoArgsConstructor;

import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "patient_id") // Relación Muchos a Uno con Patient
    @JsonBackReference(value = "patient-appointment")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "dentist_id") // Relación Muchos a Uno con Dentist
    @JsonBackReference(value = "dentist-appointment")
    private Dentist dentist;

    private LocalDate date;

}