package Alavarse.Ortega.Battery.Commerce.DTOs;

import jakarta.validation.constraints.NotBlank;

public record FreightRequestDTO(
        @NotBlank String postalReceiver
) {
}