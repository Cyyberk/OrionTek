package com.test.OrionTek.address;


import java.util.Set;

import org.springframework.stereotype.Service;

import com.test.OrionTek.address.exceptions.AddressNotFoundException;
import com.test.OrionTek.customer.Customer;
import com.test.OrionTek.customer.CustomerRepository;

import jakarta.transaction.Transactional;

@Service
public class AddressService implements DefaultAddressService{

    private AddressRepository addressRepository;
    private CustomerRepository customerRepository;

    public AddressService(AddressRepository addressRepository, CustomerRepository customerRepository){
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public Address addAddress(Address address, Customer customer) {
       customer.addAddress(address);
       addressRepository.save(address);
       customerRepository.save(customer);
       return address;
    }

    @Override
    @Transactional
    public Address removeAddress(Address address, Customer customer) {
        customer.getAddress().remove(address);
        customerRepository.save(customer);
        addressRepository.delete(address);
        return address;
    }

    @Override
    public Address getAddressById(long id) {
        return addressRepository.findById(id).orElseThrow(()-> new AddressNotFoundException("Address with id: " + id + " not found"));
    }
    

    @Override
    public Set<Address> getAllAddresses(Customer customer) {
       return customer.getAddress();
    }

    @Override
    @Transactional
    public Address updateAddress(Address address) {
      return addressRepository.save(address);
    }

    
}
