package Alavarse.Ortega.Battery.Commerce.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PromotionDTO(
        @NotNull @JsonFormat(pattern = "dd/MM/yyyy") LocalDate expirationDate,
        @NotNull Integer percentage,
        @NotBlank String code) {
}
