package com.harmony.supermarketapiproduct.application.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StockDecreaseDto {
    @NotNull
    private Long productId;

    @NotNull @Min(1) @Max(100)
    private Integer quantity;

}
