package com.dh.Dental.Clinic.repository;

import com.dh.Dental.Clinic.entity.Dentist;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository

public interface IDentistRepository extends JpaRepository<Dentist, Integer> {

}