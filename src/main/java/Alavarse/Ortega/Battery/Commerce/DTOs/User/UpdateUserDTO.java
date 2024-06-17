package Alavarse.Ortega.Battery.Commerce.DTOs.User;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserDTO(
        @NotBlank String name,
        @NotBlank String email
) {
}
