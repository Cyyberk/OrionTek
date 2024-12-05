package com.test.OrionTek.address;

import java.util.Set;

import com.test.OrionTek.customer.Customer;

public interface DefaultAddressService {
    public Address addAddress(Address address, Customer customer);
    public Address removeAddress(Address address, Customer customer);
    public Address updateAddress(Address address);
    public Address getAddressById(long id);
    public Set<Address> getAllAddresses(Customer customer);
}
