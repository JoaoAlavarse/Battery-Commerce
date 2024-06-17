package Alavarse.Ortega.Battery.Commerce.DTOs.Authentication;

import jakarta.validation.constraints.NotBlank;

public record PasswordDTO(
        @NotBlank String password
) {
}
