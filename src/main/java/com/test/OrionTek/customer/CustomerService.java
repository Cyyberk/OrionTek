package com.test.OrionTek.customer;

import java.util.List;

import org.springframework.stereotype.Service;

import com.test.OrionTek.address.Address;
import com.test.OrionTek.address.AddressRepository;

import jakarta.transaction.Transactional;

@Service
public class CustomerService implements DefaultCustomerService{

    private CustomerRepository customerRepository;
    private AddressRepository addressRepository;

    public CustomerService(CustomerRepository customerRepository, AddressRepository addressRepository){
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    @Transactional
    public Customer register(CustomerDTO customerDTO) {
        Customer customer = new Customer(customerDTO.getName(), customerDTO.getLastname(), customerDTO.getAge(), customerDTO.getGender());
        customer.addAddress(customerDTO.getAddress());
        addressRepository.save(customerDTO.getAddress());
        return customerRepository.save(customer);
    }

    @Override
    public Customer delete(Customer customer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public List<Customer> getAllCustumers() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCustumers'");
    }

    @Override
    public Customer getCustomerById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCustomer'");
    }
    
}
