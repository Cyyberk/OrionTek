package com.test.OrionTek.customer;

import java.util.List;

import com.test.OrionTek.customer.dto.CustomerDTO;

public interface DefaultCustomerService {

    public Customer register(CustomerDTO customerDTO);
    public Customer delete(Customer customer);
    public List<Customer> getAllCustomers();
    public Customer getCustomerById(Long id);
    public Customer updateCustomer(Customer customer);
}
