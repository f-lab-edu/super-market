package com.harmony.supermarketapiproduct.domain;

import com.harmony.supermarketapiproduct.application.dto.ProductDto;
import com.harmony.supermarketapiproduct.common.exception.ProductInsufficientStockException;
import com.harmony.supermarketapiproduct.common.exception.ProductQuantityNegativeException;
import com.harmony.supermarketapiproduct.common.exception.ProductStockMismatchException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String name;

    private Long price;

    private Integer stockQuantity;

    @Version
    private Long version;

    public Product(final String name, final Long price, final Integer stockQuantity){
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public ProductDto toProductDto(){ return new ProductDto(this.productId, this.name, this.price, this.stockQuantity); }

    public void decreaseStock(final Integer quantity){
        if (quantity < 0) { throw new ProductQuantityNegativeException(); }
        if (this.stockQuantity < quantity){ throw new ProductInsufficientStockException(); }

        this.stockQuantity -= quantity;
    }

    public void verifyStockAmount(final Integer expectedStock){
        if (!this.stockQuantity.equals(expectedStock)) { throw new ProductStockMismatchException(); }
    }
}
