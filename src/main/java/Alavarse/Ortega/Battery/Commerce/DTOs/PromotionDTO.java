package Alavarse.Ortega.Battery.Commerce.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record PromotionDTO(@NotBlank @JsonFormat(pattern = "dd/MM/yyyy") LocalDate expirationDate, @NotBlank Integer percentage, @NotBlank String code) {
}
