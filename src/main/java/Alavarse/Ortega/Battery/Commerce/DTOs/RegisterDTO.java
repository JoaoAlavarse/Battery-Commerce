package Alavarse.Ortega.Battery.Commerce.DTOs;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


public record RegisterDTO(@NotBlank String email, @NotBlank String password, @NotBlank String name, @NotBlank String document){
}
