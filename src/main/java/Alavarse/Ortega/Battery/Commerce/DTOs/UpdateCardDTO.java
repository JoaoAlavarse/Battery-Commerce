package Alavarse.Ortega.Battery.Commerce.DTOs;

import jakarta.validation.constraints.NotBlank;

public record UpdateCardDTO(
        @NotBlank String cardOwner,
        @NotBlank String expirationDate
) {
}
