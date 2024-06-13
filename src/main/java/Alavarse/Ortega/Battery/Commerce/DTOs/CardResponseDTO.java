package Alavarse.Ortega.Battery.Commerce.DTOs;

public record CardResponseDTO(
        String cardId,
        String partialCard,
        String flag,
        String cardOwner,
        String expirationDate,
        Boolean main
) {
}
