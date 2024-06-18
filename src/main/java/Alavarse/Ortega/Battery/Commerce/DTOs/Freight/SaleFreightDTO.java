package Alavarse.Ortega.Battery.Commerce.DTOs.Freight;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SaleFreightDTO (
        @NotBlank String postalReceiver,
        @NotNull Integer quantity
){
}
