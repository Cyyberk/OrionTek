package com.test.OrionTek.address.dto;

import com.test.OrionTek.address.Address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterAddressDTO {
    private long customerId;
    private Address address;      
}
