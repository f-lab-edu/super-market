package com.harmony.supermarketapiproduct.concurrency;

import com.harmony.supermarketapiproduct.product.application.ProductService;
import com.harmony.supermarketapiproduct.product.application.dto.StockDecreaseDto;
import com.harmony.supermarketapiproduct.product.domain.Product;
import com.harmony.supermarketapiproduct.product.domain.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ProductServiceConcurrencyTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testOptimisticLockingOnConcurrentStockDecrease() throws InterruptedException {
        // given
        final Long productId = 1L;
        productRepository.save(new Product("레예스 글러브", 100000L, 10));
        final CountDownLatch latch = new CountDownLatch(2);
        final AtomicBoolean optimisticLockExceptionCaught = new AtomicBoolean(false);
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // when
        Runnable decreaseStockTask = () -> {
            try {
                productService.decreaseStock(List.of(new StockDecreaseDto(productId, 1)));
            } catch (ObjectOptimisticLockingFailureException e) {
                optimisticLockExceptionCaught.set(true);
            } finally {
                latch.countDown();
            }
        };

        executor.execute(decreaseStockTask);
        executor.execute(decreaseStockTask);
        latch.await();
        executor.shutdown();

        // Then
        assertTrue(optimisticLockExceptionCaught.get(), "An OptimisticLockException should have been thrown due to concurrent modifications.");
    }
}