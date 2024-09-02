package com.dh.Dental.Clinic.service.impl;

import com.dh.Dental.Clinic.entity.Address;

import com.dh.Dental.Clinic.repository.IAddressRepository;

import com.dh.Dental.Clinic.service.IAddressService;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class AddressService implements IAddressService {

    private final IAddressRepository addressRepository;

    public AddressService(IAddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Optional<Address> findAddressById(Integer id) {
        return Optional.empty();
    }

    @Override
    public void updateAddress(Address address) {

    }

    @Override
    public void deleteAddress(Integer id) {

    }

}
