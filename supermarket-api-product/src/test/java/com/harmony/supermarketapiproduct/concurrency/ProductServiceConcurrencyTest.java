package com.harmony.supermarketapiproduct.concurrency;

import com.harmony.supermarketapiproduct.product.application.ProductService;
import com.harmony.supermarketapiproduct.product.domain.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductServiceConcurrencyTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

}