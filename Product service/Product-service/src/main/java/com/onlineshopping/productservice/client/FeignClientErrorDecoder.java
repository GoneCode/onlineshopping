package com.onlineshopping.productservice.client;

import com.onlineshopping.productservice.exceptionhandler.ResourceNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignClientErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 404) {
            return new ResourceNotFoundException("Category not found");
        }
        if (response.status() == 500) {
            return new ResourceNotFoundException("Category not found");
        }
        return new Exception("Generic error");
    }
}