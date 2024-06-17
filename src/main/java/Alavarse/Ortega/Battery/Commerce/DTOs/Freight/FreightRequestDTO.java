package Alavarse.Ortega.Battery.Commerce.DTOs.Freight;

import jakarta.validation.constraints.NotBlank;

public record FreightRequestDTO(
        @NotBlank String postalReceiver
) {
}
