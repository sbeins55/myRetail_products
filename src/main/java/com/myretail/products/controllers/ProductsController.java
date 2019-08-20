package com.myretail.products.controllers;

import com.myretail.products.services.ProductService;
import com.myretail.products.valueobjects.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping
public class ProductsController {

    private ProductService productService;

    @Autowired
    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/{redskyId}")
    public ResponseEntity<Product> fetchProduct(@PathVariable Long redskyId) throws IOException {
        return ResponseEntity.ok(this.productService.getProduct(redskyId));
    }

    @PutMapping("/products/{redskyId}")
    public ResponseEntity<Product> putProduct(@PathVariable Long redskyId, @RequestBody Product product ) {
        return ResponseEntity.ok(this.productService.updateProduct(redskyId, product));
    }
}
