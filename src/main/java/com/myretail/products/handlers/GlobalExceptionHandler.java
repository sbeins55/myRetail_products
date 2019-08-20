package com.myretail.products.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.myretail.products.exceptions.ProductMismatchException;
import com.myretail.products.exceptions.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private ObjectMapper mapper;

    @Autowired
    GlobalExceptionHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity handleProductNotFound() {
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(ProductMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleMismatchProducts(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorMessage(ex.getMessage()));
    }

    private ObjectNode buildErrorMessage(String message) {
        ObjectNode root = this.mapper.createObjectNode();
        root.put("message", message);
        return root;
    }
}
