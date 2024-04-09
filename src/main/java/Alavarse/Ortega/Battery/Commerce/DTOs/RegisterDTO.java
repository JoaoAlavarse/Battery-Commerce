package Alavarse.Ortega.Battery.Commerce.DTOs;

import jakarta.validation.constraints.NotBlank;


public record RegisterDTO(
        @NotBlank String email,
        @NotBlank String password,
        @NotBlank String name,
        @NotBlank String document
){
}
