package com.test.OrionTek.customer;

import java.util.HashSet;
import java.util.Set;

import com.test.OrionTek.address.Address;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String lastname;
    private int age;
    private Character gender;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "customer_address", joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name = "address_id", referencedColumnName = "id") )
    private Set<Address> address;


     public Customer(String name, String lastname, int age, Character gender){
        this.name = name;
        this.lastname = lastname;
        this.age = age;
        this.gender = gender;
        address = new HashSet<>();
    }

    public void addAddress(Address address){
        this.address.add(address);
    }

}
