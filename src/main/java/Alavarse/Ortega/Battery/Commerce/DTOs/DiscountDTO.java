package Alavarse.Ortega.Battery.Commerce.DTOs;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DiscountDTO(@NotNull @NotEmpty String userId, @NotNull @NotEmpty BigDecimal totalValue) {
}