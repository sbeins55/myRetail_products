package com.myretail.products.handlers;

import com.myretail.products.exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Component
public class RestTemplateErrorHandler implements ResponseErrorHandler {

    /**
     * Identifies whether response is an error
     *
     * @param clientHttpResponse response from HTTP request
     * @return boolean whether or not the response is an error that needs to be handled
     * @throws IOException when status code cannot be read
     */
    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
        return (clientHttpResponse.getStatusCode().is4xxClientError() || clientHttpResponse.getStatusCode().is5xxServerError());
    }

    /**
     * Determines how to handle errors from a RestTemplate call
     *
     * @param clientHttpResponse response from HTTP request
     * @throws IOException when status code cannot be read
     */
    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
        if (clientHttpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {
            throw new ProductNotFoundException();
        }
    }
}
