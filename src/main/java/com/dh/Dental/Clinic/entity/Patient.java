package com.dh.Dental.Clinic.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;

import lombok.Getter;

import lombok.NoArgsConstructor;

import lombok.Setter;

import java.time.LocalDate;

import java.util.Set;

@Getter

@Setter

@NoArgsConstructor

@AllArgsConstructor

@Entity

@Table(name = "patients")

public class Patient {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    private String lastName;

    private String firstName;

    private String dni;

    private LocalDate admissionDate;

    @OneToOne(cascade = CascadeType.ALL)

    private Address address; // Relación Uno a Uno con Address

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)

    @JsonManagedReference(value = "patient-appointment")

    private Set<Appointment> appointments; // Relación Uno a Muchos con Appointment

}