package com.harmony.supermarketapiproduct.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private Long price;
    private Integer quantityStock;
}
