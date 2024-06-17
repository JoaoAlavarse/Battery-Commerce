package Alavarse.Ortega.Battery.Commerce.DTOs.Promotion;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PromotionDTO(
        @NotNull @JsonFormat(pattern = "dd/MM/yyyy") LocalDate expirationDate,
        @NotNull @Min(0) @Max(100) Integer percentage,
        @NotBlank String code) {
}
