package com.harmony.supermarketapiproduct.application.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private Long price;
    private Integer quantityStock;
}
