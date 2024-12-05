package com.test.OrionTek.address.exceptions;

public class AddressNotFoundException extends RuntimeException{
    public AddressNotFoundException(String message){
        super(message);
    }
}
