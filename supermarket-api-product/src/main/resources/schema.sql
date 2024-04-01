-- product 테이블이 이미 존재한다면 삭제
DROP TABLE IF EXISTS `product`;

-- product 테이블 생성
CREATE TABLE `product` (
                           `product_id` BIGINT NOT NULL AUTO_INCREMENT,
                           `name` VARCHAR(255) NOT NULL,
                           `price` BIGINT NOT NULL,
                           `stock_quantity` INT NOT NULL,
                           `version` BIGINT NOT NULL DEFAULT 0,
                           PRIMARY KEY (`product_id`)
);
