package com.harmony.supermarketapiproduct.controller;

import com.harmony.supermarketapiproduct.application.ProductService;
import com.harmony.supermarketapiproduct.application.dto.ProductDto;
import com.harmony.supermarketapiproduct.application.dto.StockDecreaseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PatchMapping("/products/decrease-stock")
    public List<ProductDto> decreaseStock(@Valid @RequestBody final List<StockDecreaseDto> stockDecreaseDtos) {

        return productService.decreaseStock(stockDecreaseDtos);
    }

}