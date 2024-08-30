package com.dh.Dental.Clinic.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;

import lombok.Getter;

import lombok.NoArgsConstructor;

import lombok.Setter;

import java.util.Set;

@Getter

@Setter

@NoArgsConstructor

@AllArgsConstructor

@Entity

@Table(name = "dentists")

public class Dentist {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    private String registrationNumber;

    private String lastName;

    private String firstName;

    @OneToMany(mappedBy = "dentist", cascade = CascadeType.REMOVE)

    @JsonManagedReference(value = "dentist-appointment")

    private Set<Appointment> appointments; // Relación Uno a Muchos con Appointment

}
