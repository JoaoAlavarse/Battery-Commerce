package Alavarse.Ortega.Battery.Commerce.DTO;

import Alavarse.Ortega.Battery.Commerce.Enum.BatteryStatus;
import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;

public record BatteryDTO(@NotEmpty String name, @NotEmpty String description, @NotEmpty BigDecimal value, @NotEmpty Integer quantity, BatteryStatus status) {
}
