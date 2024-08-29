package com.dh.Dental.Clinic.repository;

import com.dh.Dental.Clinic.entity.Address;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository

public interface IAddressRepository extends JpaRepository<Address, Integer> {

}