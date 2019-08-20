package com.myretail.products.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myretail.products.exceptions.ProductMismatchException;
import com.myretail.products.repository.ProductRepository;
import com.myretail.products.valueobjects.CurrentPrice;
import com.myretail.products.valueobjects.Product;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@RestClientTest(ProductService.class)
public class ProductServiceTest {

    private static final Long redskyId = 12345678L;

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private ObjectMapper objectMapper;

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

    @Before
    public void setUp() throws Exception {
        JSONObject product = new JSONObject();
        JSONObject item = new JSONObject();
        JSONObject description = new JSONObject();
        JSONObject title = new JSONObject();

        title.put("title", "Test Title");
        description.put("product_description", title);
        item.put("item", description);
        product.put("product", item);

        this.server.expect(requestTo("https://redsky.target.com/v2/pdp/tcin/"+redskyId))
                .andRespond(withSuccess(product.toString(), MediaType.APPLICATION_JSON));
    }

    @Test
    public void getProduct() throws Exception {
        Product mockProduct = buildProduct();
        Mockito.when(productRepository.findByRedskyId(redskyId))
                .thenReturn(Optional.of(mockProduct));

        Product productFound = productService.getProduct(redskyId);

        assertEquals(productFound, mockProduct);
    }

    @Test
    public void updateProduct() {
        Product mockProduct = buildProduct();
        Product updatedProduct = buildUpdatedProduct();
        Mockito.when(productRepository.findByRedskyId(redskyId))
                .thenReturn(Optional.of(mockProduct));

        Mockito.when(productRepository.save(mockProduct))
                .thenReturn(updatedProduct);

        Product serviceProduct = productService.updateProduct(redskyId, mockProduct);
        assertEquals(serviceProduct, updatedProduct);
    }

    @Test(expected = ProductMismatchException.class)
    public void updateProductWithMismatchId() {
        Product mockProduct = buildProduct();
        Long badRedskyId = 1234L;

        productService.updateProduct(badRedskyId, mockProduct);
    }
}