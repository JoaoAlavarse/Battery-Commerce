package Alavarse.Ortega.Battery.Commerce.DTOs;

import Alavarse.Ortega.Battery.Commerce.Enums.UserStatus;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserDTO(
        @NotBlank String password,
        @NotBlank String name,
        @NotBlank UserStatus status
) {
}
