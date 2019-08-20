package com.myretail.products.exceptions;

/**
 * Exception if a Product cannot be found
 */
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException() {
        super();
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}
