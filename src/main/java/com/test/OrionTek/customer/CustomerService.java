package com.test.OrionTek.customer;

import java.util.List;

import org.springframework.stereotype.Service;

import com.test.OrionTek.address.Address;
import com.test.OrionTek.address.AddressRepository;
import com.test.OrionTek.customer.exceptions.CustomerIdNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class CustomerService implements DefaultCustomerService{

    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public Customer register(CustomerDTO customerDTO) {
        Customer customer = new Customer(customerDTO.getName(), customerDTO.getLastname(), customerDTO.getAge(), customerDTO.getGender());
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
        return customerRepository.findById(id).orElseThrow(()-> new CustomerIdNotFoundException("Customer not found with this id: " + id));
    }
    
}
