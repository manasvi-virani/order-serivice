package com.muvecommerce.controller;

import com.muvecommerce.dto.ProductRequest;
import com.muvecommerce.dto.ProductResponse;
import com.muvecommerce.services.ProductAlreadyExistsException;
import com.muvecommerce.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@Valid @RequestBody ProductRequest productRequest) throws ProductAlreadyExistsException {
        productService.createProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getProducts() {

        return productService.getAllProducts();


    }
}
