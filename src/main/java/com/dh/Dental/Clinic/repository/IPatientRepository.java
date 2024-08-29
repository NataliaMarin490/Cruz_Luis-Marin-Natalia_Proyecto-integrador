package com.dh.Dental.Clinic.repository;

import com.dh.Dental.Clinic.entity.Patient;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository

public interface IPatientRepository extends JpaRepository<Patient, Integer> {

}
