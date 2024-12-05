package com.test.OrionTek.customer;

import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class CustomerController {
    
    private CustomerService customerService;

    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }



    @PostMapping("/customers/register")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> registerCustomer(@Valid @RequestBody CustomerDTO customerForm) {
        Customer customer = customerService.register(customerForm);
        return generateRespose("New customer added successfully!", HttpStatus.CREATED, customer);
    }
    
    private ResponseEntity<Object> generateRespose(String message, HttpStatus status, Object responseobj) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", message);
		map.put("status", status.value());
		map.put("data", responseobj);
		return new ResponseEntity<Object>(map, status);
	}

}
