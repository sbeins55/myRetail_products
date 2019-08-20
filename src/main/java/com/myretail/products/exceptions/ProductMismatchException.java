package com.myretail.products.exceptions;

/**
 * Exception for when identifiers for a Product does not match
 */
public class ProductMismatchException extends RuntimeException {

    public ProductMismatchException(String message) {
        super(message);
    }
}
