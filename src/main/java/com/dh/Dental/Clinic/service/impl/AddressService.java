package com.dh.Dental.Clinic.service.impl;

import com.dh.Dental.Clinic.entity.Address;

import com.dh.Dental.Clinic.repository.IAddressRepository;

import com.dh.Dental.Clinic.service.IAddressService;

import org.springframework.stereotype.Service;

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

}
