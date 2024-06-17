package Alavarse.Ortega.Battery.Commerce.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SaleDTO(
        @NotNull @Min(1) BigDecimal value,
        @NotNull @Min(1) BigDecimal freightValue,
        @NotBlank String addressId,
        @NotBlank String userId,
        @NotBlank String cartId,
        String promotionId){
}
