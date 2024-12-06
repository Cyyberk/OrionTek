package com.test.OrionTek.address.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteAddressDTO {
    private long customerId;
    private long addressId;      
}
