package com.muvecommerce.services;

public class ProductAlreadyExistsException extends Throwable {
    public ProductAlreadyExistsException(String s) {
        super(s);
    }
}
