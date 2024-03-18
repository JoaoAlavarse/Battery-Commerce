package Alavarse.Ortega.Battery.Commerce.DTOs;

import Alavarse.Ortega.Battery.Commerce.Enums.BatteryStatus;
import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;

public record BatteryDTO(@NotEmpty String name, @NotEmpty String description, @NotEmpty BigDecimal value, @NotEmpty Integer quantity, BatteryStatus status) {
}
