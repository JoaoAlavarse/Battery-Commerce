package Alavarse.Ortega.Battery.Commerce.DTOs.Authentication;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO(
        @NotBlank String email,
        @NotBlank String password
) {
}
