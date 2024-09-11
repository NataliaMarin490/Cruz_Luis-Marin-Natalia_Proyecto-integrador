package com.dh.Dental.Clinic.repository;

import com.dh.Dental.Clinic.entity.Dentist;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface IDentistRepository extends JpaRepository<Dentist, Integer> {

    @Query("SELECT d FROM Dentist d WHERE d.registrationNumber = :registrationNumber")
    Optional<Dentist> findByRegistrationNumber(@Param("registrationNumber") String registrationNumber);

}
