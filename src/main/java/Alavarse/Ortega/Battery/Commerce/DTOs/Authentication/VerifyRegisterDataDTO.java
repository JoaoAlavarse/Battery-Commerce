package Alavarse.Ortega.Battery.Commerce.DTOs.Authentication;

import jakarta.validation.constraints.NotBlank;

public record VerifyRegisterDataDTO(
        @NotBlank String email,
        @NotBlank String name,
        @NotBlank String document
) {
}
