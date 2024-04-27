package Alavarse.Ortega.Battery.Commerce.DTOs;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record DiscountDTO(
        @NotBlank String userId,
        @NotBlank @Min(0) BigDecimal totalValue
) {
}
