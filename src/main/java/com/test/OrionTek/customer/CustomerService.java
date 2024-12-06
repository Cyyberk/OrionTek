package com.test.OrionTek.customer;

import java.util.List;

import org.springframework.stereotype.Service;

import com.test.OrionTek.customer.dto.CustomerDTO;
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
    @Transactional
    public Customer delete(Customer customer) {
        customerRepository.delete(customer);
        return customer;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(()-> new CustomerIdNotFoundException("Customer not found with this id: " + id));
    }

    @Override
    @Transactional
    public Customer updateCustomer(Customer customer) {
       return customerRepository.save(customer);
    }
    
}
