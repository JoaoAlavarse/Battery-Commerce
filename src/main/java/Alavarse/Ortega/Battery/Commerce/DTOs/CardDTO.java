package Alavarse.Ortega.Battery.Commerce.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CardDTO(
        @NotBlank String cardNumber,
        @NotBlank String cardOwner,
        @NotBlank String ownerDocument,
        @NotBlank String expirationDate,
        @NotBlank @Size(max = 3, min = 3) String cvv,
        @NotBlank String userId,
        @NotNull Boolean main

) {
}
