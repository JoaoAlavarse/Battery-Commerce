package Alavarse.Ortega.Battery.Commerce.DTO;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record PromotionDTO(@NotBlank LocalDate expirationDate, @NotBlank Integer percentage, @NotBlank String code) {
}
