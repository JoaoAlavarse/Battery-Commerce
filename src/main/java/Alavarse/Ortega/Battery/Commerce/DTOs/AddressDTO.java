package Alavarse.Ortega.Battery.Commerce.DTOs;

import jakarta.validation.constraints.NotBlank;

public record AddressDTO(
        @NotBlank String address,
        @NotBlank String number,
        @NotBlank String neighborhood,
        String complement,
        @NotBlank String city,
        @NotBlank String state,
        @NotBlank String CEP,
        @NotBlank String userId) {
}
