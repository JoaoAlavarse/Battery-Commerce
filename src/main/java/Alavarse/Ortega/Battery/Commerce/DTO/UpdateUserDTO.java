package Alavarse.Ortega.Battery.Commerce.DTO;

import Alavarse.Ortega.Battery.Commerce.Enum.UserStatus;

public record UpdateUserDTO(String password, String name, UserStatus status) {
}
