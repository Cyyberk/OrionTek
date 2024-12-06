package com.test.OrionTek.customer.dto;

import com.test.OrionTek.address.Address;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDTO {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    
    @Size(min = 2, max = 20)
    private String name;
    
    @Size(min = 2, max = 20)
    private String lastname;
    
    @Email(message = "Please provide a valid email.")
    private String email;

    private int age;
    private Character gender;
    private Address address;

   

}
