package com.myretail.products.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myretail.products.exceptions.ProductMismatchException;
import com.myretail.products.exceptions.ProductNotFoundException;
import com.myretail.products.handlers.RestTemplateErrorHandler;
import com.myretail.products.repository.ProductRepository;
import com.myretail.products.valueobjects.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class ProductService {

    private ProductRepository productRepository;

    private ObjectMapper mapper;

    private RestTemplate restTemplate;

    @Value("${myRetail.internal.product.info.api}")
    private String redSkyResourceURL;

    @Autowired
    public ProductService(ProductRepository productRepository, ObjectMapper mapper, RestTemplateBuilder templateBuilder) {
        this.productRepository = productRepository;
        this.mapper = mapper;
        this.restTemplate = templateBuilder
                .errorHandler(new RestTemplateErrorHandler())
                .build();
    }

    /**
     * Fetches the product by redskyId and returns information about price and name
     *
     * @param redskyId the identifier for a redsky product
     * @return Product a JSON object with name and price information
     * @throws IOException when JSON response cannot be read
     */
    public Product getProduct(Long redskyId) throws IOException {

        // Get product from redsky
        ResponseEntity<String> response = this.restTemplate.getForEntity(redSkyResourceURL + redskyId.toString(), String.class);
        JsonNode root = this.mapper.readTree(response.getBody());
        JsonNode productName = root.path("product").path("item").path("product_description").path("title");

        // Get Product in data store
        Product product = this.productRepository.findByRedskyId(redskyId).orElseThrow(ProductNotFoundException::new);
        product.setName(productName.asText());

        return product;
    }

    /**
     * Updates the price information of a product identified by the redsky Identifier
     *
     * @param redskyId the redsky identifier for a product
     * @param product a product with updated price information
     * @return Product the product after updates to the price
     */
    public Product updateProduct(Long redskyId, Product product) {
        if (!product.getRedskyId().equals(redskyId)) {
            throw new ProductMismatchException("Product: " + product.toString() + " does not have redskyId: " + redskyId.toString());
        }

        // Get Product from data store and update the price
        Product repositoryProduct = this.productRepository.findByRedskyId(redskyId).orElseThrow(ProductNotFoundException::new);
        repositoryProduct.setCurrent_price(product.getCurrent_price());

        // Save Updated product with attached ID
        Product updatedProduct = this.productRepository.save(repositoryProduct);
        updatedProduct.setName(product.getName());

        return updatedProduct;
    }
}
