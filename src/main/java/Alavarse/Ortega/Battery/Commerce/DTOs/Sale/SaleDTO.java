package Alavarse.Ortega.Battery.Commerce.DTOs.Sale;

import jakarta.validation.constraints.NotBlank;

public record SaleDTO(
        @NotBlank String addressId,
        @NotBlank String userId,
        @NotBlank String cartId,
        @NotBlank String cep,
        String promotionId){
}
