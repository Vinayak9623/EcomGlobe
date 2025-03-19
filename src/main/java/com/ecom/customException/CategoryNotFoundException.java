package com.ecom.customException;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(String message) {

        super(message);
    }
}
