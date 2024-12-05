package com.test.OrionTek.address;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.test.OrionTek.address.dto.DeleteAddressDTO;
import com.test.OrionTek.address.dto.RegisterAddressDTO;
import com.test.OrionTek.customer.Customer;
import com.test.OrionTek.customer.CustomerService;

@Controller
public class AddressController {
 
    private CustomerService customerService;
    private AddressService addressService;

    public AddressController(CustomerService customerService, AddressService addressService){
        this.customerService = customerService;
        this.addressService = addressService;
    }

    @GetMapping("/customer/address/{addressId}")
    public ResponseEntity<Object> getAddress(@PathVariable Long addressId) {
        Address address = addressService.getAddressById(addressId);
        return generateRespose("List of address for this customer", HttpStatus.OK, address);    
    }

    @GetMapping("/customer/address/all/{customerId}")
    public ResponseEntity<Object> getAllAddresses(@PathVariable Long customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        Set<Address> addresses = addressService.getAllAddresses(customer);
        return generateRespose("List of address for this customer", HttpStatus.OK, addresses);
    }

     // Adding new address to the customer by the attribute "customerId" of the address
    @PostMapping("/customer/address")
    public ResponseEntity<Object> registerAddress(@RequestBody RegisterAddressDTO addressDTO) {
      
        Customer customer = customerService.getCustomerById(addressDTO.getCustomerId());
        addressService.addAddress(addressDTO.getAddress(), customer);
        return generateRespose("List of address for this customer", HttpStatus.OK, customer.getAddress());
    }

      // Updating address
      @PatchMapping("/customer/address")
      @PreAuthorize("hasAuthority('ADMIN')")
      public ResponseEntity<Object> updateAddress(@RequestBody Address updatedAddress) {
          Address address = addressService.getAddressById(updatedAddress.getId());
          if(address != null) addressService.updateAddress(updatedAddress);
          return generateRespose("List of address for this customer", HttpStatus.OK, address);
      }

    @DeleteMapping("/customer/address")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> deleteAddress(@RequestBody DeleteAddressDTO deleteDTO) {
        Customer customer = customerService.getCustomerById(deleteDTO.getCustomerId());
        Address address = addressService.getAddressById(deleteDTO.getAddressId());     
        if(customer != null && address != null)
            addressService.removeAddress(address, customer);      
        return generateRespose("List of address for this customer", HttpStatus.OK, address);
    }


     private ResponseEntity<Object> generateRespose(String message, HttpStatus status, Object responseobj) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", message);
		map.put("status", status.value());
		map.put("data", responseobj);
		return new ResponseEntity<Object>(map, status);
	}
    


}
