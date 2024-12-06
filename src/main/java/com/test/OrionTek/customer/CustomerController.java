package com.test.OrionTek.customer;

import org.springframework.web.bind.annotation.RestController;

import com.test.OrionTek.address.Address;
import com.test.OrionTek.address.AddressService;
import com.test.OrionTek.customer.dto.CustomerDTO;
import com.test.OrionTek.customer.dto.DeleteCustomerDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping(path = "${api.version}/customer")
@Tag(name = "Customer Controller", description = "Operations related with the manadgement of customers of OrionTek")
public class CustomerController {
    
    private CustomerService customerService;
    private AddressService addressService;

    public CustomerController(CustomerService customerService, AddressService addressService){
        this.customerService = customerService;
        this.addressService = addressService;
    }

    // Documentation for swagger-ui. Check them by accesing: localhost:port/swagger-ui/index.html#/
    @Operation(summary = "Retrieve All Customers", description = "Get all registered customers",
    responses = {@io.swagger.v3.oas.annotations.responses.ApiResponse(content = @Content(mediaType = "application/json"))})

    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('USER')")
    public ResponseEntity<Object> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return generateRespose("Listing all customers", HttpStatus.OK, customers);
    }


    @Operation(summary = "Retrieve Single Customer", description = "Get customer info given its id",
    responses = {@io.swagger.v3.oas.annotations.responses.ApiResponse(content = @Content(mediaType = "application/json"))})

    @GetMapping("/{customerId}")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('USER')")
    public ResponseEntity<Object> getCustomer(@PathVariable Long customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        return generateRespose("Listing customer", HttpStatus.OK, customer);    
    }


    @Operation(summary = "Register New Customer",  description = "Add a new customer. Its address will get registered too.",
    responses = {@io.swagger.v3.oas.annotations.responses.ApiResponse(content = @Content(mediaType = "application/json"))})

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

  
    @Operation(summary = "Update Customer",  description = "Update the customer. If you try to provide a new address in the json, the addresses will be the current registered for that customer. Addresses must be updated with the correct endpoint",
    responses = {@io.swagger.v3.oas.annotations.responses.ApiResponse(content = @Content(mediaType = "application/json"))})
    
    @PatchMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> updateCustomer(@RequestBody Customer updatedCustomer) {
        Customer customer = customerService.getCustomerById(updatedCustomer.getId());
        updatedCustomer.setAddress(customer.getAddress());
        if(customer != null) customerService.updateCustomer(updatedCustomer);
        return generateRespose("Updated customer", HttpStatus.OK, customer);
    }
    

    @Operation(summary = "Delete Customer",  description = "Delete customer by providing only its id in the json body. Keep in mind this will delete the addresses associated with that customer too",
    responses = {@io.swagger.v3.oas.annotations.responses.ApiResponse(content = @Content(mediaType = "application/json"))})

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
