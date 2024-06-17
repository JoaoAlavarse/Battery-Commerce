package Alavarse.Ortega.Battery.Commerce.DTOs.Payment;

import Alavarse.Ortega.Battery.Commerce.DTOs.Sale.SaleDTO;
import jakarta.validation.constraints.NotBlank;

public record PaymentPixRequestDTO(
        @NotBlank String fmp_description,
        @NotBlank String fmp_value,
        SaleDTO saleData
) {
}
