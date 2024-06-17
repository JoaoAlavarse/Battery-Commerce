package Alavarse.Ortega.Battery.Commerce.DTOs.Address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateAddressDTO(
        @NotBlank String address,
        @NotBlank String number,
        @NotBlank String neighborhood,
        String complement,
        @NotBlank String city,
        @NotBlank String state,
        @NotBlank String CEP,
        @NotNull Boolean main
) {
}
