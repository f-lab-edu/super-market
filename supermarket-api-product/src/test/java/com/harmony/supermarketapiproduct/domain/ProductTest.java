package com.harmony.supermarketapiproduct.domain;

import com.harmony.supermarketapiproduct.common.exception.ProductInsufficientStockException;
import com.harmony.supermarketapiproduct.common.exception.ProductQuantityNegativeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    @DisplayName("성공 테스트")
    @Nested
    class SuccessTest{
        @DisplayName("재고 감소 성공 로직 테스트 정상")
        @Test
        void decreaseStockSuccess() {
            // given
            Product product = new Product("레예스 글러브", 3000L, 3);

            // when
            product.decreaseStock(2);

            // then
            Integer expectedStockQuantity = 1;
            assertDoesNotThrow(() -> product.verifyStockAmount(expectedStockQuantity));
        }
    }

    @DisplayName("실패 테스트")
    @Nested
    class FailTest{
        @DisplayName("재고 감소의 결과가 재고를 0 미만으로 만들 경우 예외 발생")
        @Test
        void decreaseStockBelowZeroThrowsException(){
            // given
            Product soldoutProduct = new Product("레예스 글러브", 3000L, 0);
            Product noSoldoutProduct = new Product("레예스 글러브", 3000L, 3);

            // when & then
            assertThrows(ProductInsufficientStockException.class, () -> soldoutProduct.decreaseStock(1));
            assertThrows(ProductInsufficientStockException.class, () -> noSoldoutProduct.decreaseStock(10));
        }

        @DisplayName("재고 감소 수량은 음수 미허용")
        @Test
        void decreaseStockWithNegativeValues_ThrowException() {
            // given
            Product product = new Product("레예스 글러브", 3000L, 0);

            // when & then
            assertThrows(ProductQuantityNegativeException.class, () -> product.decreaseStock(-1));
        }
    }
}