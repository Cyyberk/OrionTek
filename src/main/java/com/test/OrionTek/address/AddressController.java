package com.test.OrionTek.address;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.springframework.web.bind.annotation.RestController;

import com.test.OrionTek.address.dto.DeleteAddressDTO;
import com.test.OrionTek.address.dto.RegisterAddressDTO;
import com.test.OrionTek.customer.Customer;
import com.test.OrionTek.customer.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "${api.version}")
@Tag(name = "Address Controller", description = "Operations related with the addresses of the customers")
public class AddressController {
 
    private CustomerService customerService;
    private AddressService addressService;

    public AddressController(CustomerService customerService, AddressService addressService){
        this.customerService = customerService;
        this.addressService = addressService;
    }


    // Documentation for swagger-ui. Check them accesing: localhost:port/swagger-ui/index.html#/
    @Operation(summary = "Get All Addresses",  description = "Retrieve all the addresses registered in the system",
    responses = {@io.swagger.v3.oas.annotations.responses.ApiResponse(content = @Content(mediaType = "application/json"))})

    @GetMapping("/address")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('USER')")
    public ResponseEntity<Object> getAllAddresses() {
        List<Address> addresses = addressService.getAllAddresses();
        return generateRespose("Listed all addresses", HttpStatus.OK, addresses);
    }


    @Operation(summary = "Get Single Address",  description = "Retrieve the requested address with the provided id",
    responses = {@io.swagger.v3.oas.annotations.responses.ApiResponse(content = @Content(mediaType = "application/json"))})

    @GetMapping("/customer/address/{addressId}")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('USER')")
    public ResponseEntity<Object> getAddress(@PathVariable Long addressId) {
        Address address = addressService.getAddressById(addressId);
        return generateRespose("Listed single address for this customer", HttpStatus.OK, address);    
    }


    @Operation(summary = "Get All Addresses From Customer",  description = "Retrieve all the addresses associated of a customer by providing the customer's id",
    responses = {@io.swagger.v3.oas.annotations.responses.ApiResponse(content = @Content(mediaType = "application/json"))})

    @GetMapping("/customer/address/all/{customerId}")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('USER')")
    public ResponseEntity<Object> getAllAddressesCustomer(@PathVariable Long customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        Set<Address> addresses = addressService.getAllCustomerAddresses(customer);
        return generateRespose("Listed all address for this customer", HttpStatus.OK, addresses);
    }


    @Operation(summary = "Register New Address",  description = "Adding new address to the customer by providing the attribute 'customerId' and the address json info ",
    responses = {@io.swagger.v3.oas.annotations.responses.ApiResponse(content = @Content(mediaType = "application/json"))})

    @PostMapping("/customer/address")
	@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> registerAddress(@RequestBody RegisterAddressDTO addressDTO) {
        Customer customer = customerService.getCustomerById(addressDTO.getCustomerId());
        addressService.addAddress(addressDTO.getAddress(), customer);
        return generateRespose("Customer registered successfully!", HttpStatus.OK, customer.getAddress());
    }


    @Operation(summary = "Update Address", responses = {@io.swagger.v3.oas.annotations.responses.ApiResponse(content = @Content(mediaType = "application/json"))})

    @PatchMapping("/customer/address")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> updateAddress(@RequestBody Address updatedAddress) {
        Address address = addressService.getAddressById(updatedAddress.getId());
        if(address != null) addressService.updateAddress(updatedAddress);
        return generateRespose("Address updated successfully", HttpStatus.OK, address);
    }

    
    @Operation(summary = "Delete Address",  description = "Delete address by providing only its id in the json body. It will not longer appear in the customer's list of addresses either",
    responses = {@io.swagger.v3.oas.annotations.responses.ApiResponse(content = @Content(mediaType = "application/json"))})

    @DeleteMapping("/customer/address")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> deleteAddress(@RequestBody DeleteAddressDTO deleteDTO) {
        Customer customer = customerService.getCustomerById(deleteDTO.getCustomerId());
        Address address = addressService.getAddressById(deleteDTO.getAddressId());     
        if(customer != null && address != null)
            addressService.removeAddress(address, customer);      
        return generateRespose("Address deleted successfully", HttpStatus.OK, address);
    }


    private ResponseEntity<Object> generateRespose(String message, HttpStatus status, Object responseobj) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", message);
		map.put("status", status.value());
		map.put("data", responseobj);
		return new ResponseEntity<Object>(map, status);
	}
    


}
