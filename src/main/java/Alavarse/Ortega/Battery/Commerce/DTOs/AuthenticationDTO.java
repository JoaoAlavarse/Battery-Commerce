package Alavarse.Ortega.Battery.Commerce.DTOs;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO(@NotBlank String email, @NotBlank String password) {
}
