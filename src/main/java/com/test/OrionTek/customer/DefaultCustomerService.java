package com.test.OrionTek.customer;

import java.util.List;

public interface DefaultCustomerService {

    public Customer register(CustomerDTO customerDTO);
    public Customer delete(Customer customer);
    public List<Customer> getAllCustumers();
    public Customer getCustomerById(Long id);
}
