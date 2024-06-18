package Alavarse.Ortega.Battery.Commerce.DTOs.Payment.Card;

import Alavarse.Ortega.Battery.Commerce.DTOs.Sale.SaleDTO;
import jakarta.validation.constraints.NotBlank;

public record PaymentCardRequestDTO(
    @NotBlank String fmc_description,
    @NotBlank String cardId,
    SaleDTO saleData
) {
}
