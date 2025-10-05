package com.muvecommerce.services;

import com.muvecommerce.dto.ProductRequest;
import com.muvecommerce.dto.ProductResponse;
import com.muvecommerce.entity.Product;
import com.muvecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest) throws ProductAlreadyExistsException {

        Optional<Product> existingProduct = productRepository.findByName(productRequest.getName());
        if (existingProduct.isEmpty()) {
            Product product = Product.builder()
                    .name(productRequest.getName())
                    .description(productRequest.getDescription())
                    .price(productRequest.getPrice())
                    .build();
            productRepository.save(product);
            log.info("Product Created Successfully{}", product);
        }
        else {

            throw new ProductAlreadyExistsException("Product with name '" + productRequest.getName() + "' already exists.");
        }
        log.info("Product Created Successfully{}", existingProduct);


    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
      return  products.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        return  ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
