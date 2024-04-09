package Alavarse.Ortega.Battery.Commerce.DTOs;

import Alavarse.Ortega.Battery.Commerce.Enums.BatteryStatus;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record BatteryDTO(
        @NotBlank String name,
        @NotBlank String description,
        @NotBlank BigDecimal value,
        @NotBlank Integer quantity,
        BatteryStatus status
) {
}
