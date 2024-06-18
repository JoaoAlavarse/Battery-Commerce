package Alavarse.Ortega.Battery.Commerce.DTOs.Payment;

import Alavarse.Ortega.Battery.Commerce.DTOs.Sale.SaleDTO;
import jakarta.validation.constraints.NotBlank;

public record PaymentCardRequestDTO(
    @NotBlank String fmc_description,
    @NotBlank String fmc_user_name,
    @NotBlank String fmc_user_document,
    @NotBlank String value,
    @NotBlank String cardId,
    SaleDTO saleData
) {
}
