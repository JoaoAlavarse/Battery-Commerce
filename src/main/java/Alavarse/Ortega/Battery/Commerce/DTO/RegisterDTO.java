package Alavarse.Ortega.Battery.Commerce.DTO;

import Alavarse.Ortega.Battery.Commerce.Enum.UserRole;
import jakarta.validation.constraints.NotBlank;

public record RegisterDTO(@NotBlank String email, @NotBlank String password, @NotBlank String name, @NotBlank String document) {
}
