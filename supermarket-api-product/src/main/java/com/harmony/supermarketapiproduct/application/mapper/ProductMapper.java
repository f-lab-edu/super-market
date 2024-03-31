package com.harmony.supermarketapiproduct.application.mapper;

import com.harmony.supermarketapiproduct.application.dto.ProductDto;
import com.harmony.supermarketapiproduct.domain.Product;

public class ProductMapper {

    public static ProductDto toDecreaseProductDto(Product decreasedProduct){
        if (decreasedProduct == null) {
            return null;
        }

        return new ProductDto(decreasedProduct.getProductId(), decreasedProduct.getName(), decreasedProduct.getPrice(), decreasedProduct.getStockQuantity());
    }

}
