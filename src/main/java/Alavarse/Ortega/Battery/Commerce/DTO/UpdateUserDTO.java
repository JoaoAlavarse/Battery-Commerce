package Alavarse.Ortega.Battery.Commerce.DTO;

import Alavarse.Ortega.Battery.Commerce.Enum.UserStatus;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserDTO(@NotBlank String password, @NotBlank String name, @NotBlank UserStatus status) {
}
