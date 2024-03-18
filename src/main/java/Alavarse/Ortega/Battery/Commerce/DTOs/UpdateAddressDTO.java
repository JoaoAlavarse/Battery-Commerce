package Alavarse.Ortega.Battery.Commerce.DTOs;

import jakarta.validation.constraints.NotBlank;

public record UpdateAddressDTO(@NotBlank String address, @NotBlank String number, @NotBlank String complement, @NotBlank String city, @NotBlank String state, @NotBlank String CEP) {
}
