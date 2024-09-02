package com.dh.Dental.Clinic.service;

import com.dh.Dental.Clinic.entity.Address;

import java.util.Optional;

public interface IAddressService {

    Address saveAddress(Address address);

    Optional<Address> findAddressById(Integer id);

    void updateAddress(Address address);

    void deleteAddress(Integer id);
}