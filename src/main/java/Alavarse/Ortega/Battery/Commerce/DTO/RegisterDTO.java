package Alavarse.Ortega.Battery.Commerce.DTO;

import Alavarse.Ortega.Battery.Commerce.Enum.UserRole;

public record RegisterDTO(String email, String password, String name, String document, UserRole role) {
}
