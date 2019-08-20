package com.myretail.products.controllers;

import com.myretail.products.services.ProductService;
import com.myretail.products.valueobjects.CurrentPrice;
import com.myretail.products.valueobjects.Product;
import org.apache.coyote.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductsControllerUnitTest {

    private static final Long redskyId = 12345678L;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductsController productsController = new ProductsController(productService);

    private Product buildProduct() {
        Product product = new Product();
        CurrentPrice price = new CurrentPrice();

        price.setValue(new BigDecimal(24));
        price.setCurrency_code("USD");

        product.setId("12345");
        product.setRedskyId(redskyId);
        product.setName("Test Product");
        product.setCurrent_price(price);

        return product;
    }

    private Product buildUpdatedProduct() {
        Product product = new Product();
        CurrentPrice price = new CurrentPrice();

        price.setValue(new BigDecimal(20));
        price.setCurrency_code("USD");

        product.setId("12345");
        product.setRedskyId(redskyId);
        product.setName("Test Product");
        product.setCurrent_price(price);

        return product;
    }

    @Test
    public void fetchProduct() throws IOException {
        Product product = buildProduct();
        Mockito.when(productService.getProduct(redskyId))
                .thenReturn(product);

        ResponseEntity responseEntity = productsController.fetchProduct(redskyId);

        assertEquals(responseEntity.getBody(), product);
    }

    @Test
    public void putProduct() {
        Product product = buildProduct();
        Product updatedProduct = buildUpdatedProduct();
        Mockito.when(productService.updateProduct(redskyId, product))
                .thenReturn(updatedProduct);

        ResponseEntity response = productsController.putProduct(redskyId, product);

        assertEquals(response.getBody(), updatedProduct);
    }
}