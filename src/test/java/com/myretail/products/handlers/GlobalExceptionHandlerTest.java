package com.myretail.products.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class GlobalExceptionHandlerTest {

    private ObjectMapper testMapper = new ObjectMapper();

    @Mock
    private ObjectMapper mapper;

    @InjectMocks
    private GlobalExceptionHandler handler = new GlobalExceptionHandler(mapper);

    @Test
    public void handleProductNotFound() {
        assertEquals(handler.handleProductNotFound(), ResponseEntity.noContent().build());
    }

    @Test
    public void handleMismatchProducts() {
        Mockito.when(mapper.createObjectNode()).thenReturn(testMapper.createObjectNode());

        ResponseEntity response = handler.handleMismatchProducts(new RuntimeException("Product ID does not match"));

        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertNotNull(response.getBody());
    }
}