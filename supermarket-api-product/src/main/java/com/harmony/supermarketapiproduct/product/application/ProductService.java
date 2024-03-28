package com.harmony.supermarketapiproduct.product.application;


import com.harmony.supermarketapiproduct.product.application.dto.ProductDto;
import com.harmony.supermarketapiproduct.product.application.dto.StockDecreaseDto;
import com.harmony.supermarketapiproduct.product.common.exception.ProductNotFoundException;
import com.harmony.supermarketapiproduct.product.common.exception.ProductStockUpdateException;
import com.harmony.supermarketapiproduct.product.domain.Product;
import com.harmony.supermarketapiproduct.product.domain.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final long MAX_RETRY_ATTEMPTS = 3;

    @Transactional
    public List<ProductDto> decreaseStock(final List<StockDecreaseDto> stockDecreaseDtos) throws ObjectOptimisticLockingFailureException {
        List<ProductDto> productDtoList = new ArrayList<>();

        for (StockDecreaseDto stockDecreaseDto: stockDecreaseDtos){
            boolean success = false;
            int attempts = 0;

            while(!success && attempts < MAX_RETRY_ATTEMPTS) {
                try {
                    Product product = productRepository.findById(stockDecreaseDto.getProductId())
                            .orElseThrow(() -> {
                                log.info("Product not found: " + stockDecreaseDto.getProductId());
                                return new ProductNotFoundException();
                            });

                    product.decreaseStock(stockDecreaseDto.getQuantity());
                    productRepository.save(product);
                    productDtoList.add(product.toProductDto());
                    success = true;
                } catch (ObjectOptimisticLockingFailureException e){
                    attempts ++;
                    if (attempts >= MAX_RETRY_ATTEMPTS) {
                        log.error("Failed to decrease stock after " + attempts + " attempts for product ID " + stockDecreaseDto.getProductId(), e);
                        throw new ProductStockUpdateException();
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ie){
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
        return productDtoList;
    }
}
