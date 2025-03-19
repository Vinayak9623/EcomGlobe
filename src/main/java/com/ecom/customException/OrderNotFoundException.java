package com.ecom.customException;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(String message) {

        super(message);
    }
}
