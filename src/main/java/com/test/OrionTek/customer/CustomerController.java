package com.test.OrionTek.customer;

import org.springframework.web.bind.annotation.RestController;

import com.test.OrionTek.address.Address;
import com.test.OrionTek.address.AddressService;
import com.test.OrionTek.customer.dto.CustomerDTO;
import com.test.OrionTek.customer.dto.DeleteCustomerDTO;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@RequestMapping(path = "/customer")
public class CustomerController {
    
    private CustomerService customerService;
    private AddressService addressService;

    public CustomerController(CustomerService customerService, AddressService addressService){
        this.customerService = customerService;
        this.addressService = addressService;
    }

    @GetMapping("")
    public ResponseEntity<Object> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return generateRespose("Listing all customers", HttpStatus.OK, customers);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Object> getCustomer(@PathVariable Long customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        return generateRespose("Listing customer", HttpStatus.OK, customer);    
    }


    // Registering new customer
    @PostMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> registercustomer(@Valid @RequestBody CustomerDTO customerForm) {
        Customer customer = customerService.register(customerForm);
        // Joining the address of the form  to the registered customer
        if(customerForm.getAddress() != null && customer != null){
            Address address = customerForm.getAddress();
            addressService.addAddress(address, customer);
        }
        return generateRespose("New customer added successfully!", HttpStatus.CREATED, customer);
    }

  
    // Updating customer
    @PatchMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> updateCustomer(@RequestBody Customer updatedCustomer) {
        Customer customer = customerService.getCustomerById(updatedCustomer.getId());
        updatedCustomer.setAddress(customer.getAddress());
        if(customer != null) customerService.updateCustomer(updatedCustomer);
        return generateRespose("Updated customer", HttpStatus.OK, customer);
    }
    
    @DeleteMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> deleteCustomer(@RequestBody DeleteCustomerDTO deleteDTO) {
        Customer customer = customerService.getCustomerById(deleteDTO.getCustomerId());
        if(customer != null) customerService.delete(customer);      
        return generateRespose("Deleted customer", HttpStatus.OK, customer);
    }

    private ResponseEntity<Object> generateRespose(String message, HttpStatus status, Object responseobj) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", message);
		map.put("status", status.value());
		map.put("data", responseobj);
		return new ResponseEntity<Object>(map, status);
	}

}
