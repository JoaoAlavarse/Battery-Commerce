package Alavarse.Ortega.Battery.Commerce.DTOs;

import Alavarse.Ortega.Battery.Commerce.Enums.BatteryStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record BatteryDTO(
        @NotBlank String name,
        @NotBlank String description,
        @NotNull  BigDecimal value,
        @NotNull Integer quantity,
        BatteryStatus status
) {
}
