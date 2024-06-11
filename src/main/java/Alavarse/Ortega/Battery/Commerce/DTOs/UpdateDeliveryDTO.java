package Alavarse.Ortega.Battery.Commerce.DTOs;

import Alavarse.Ortega.Battery.Commerce.Enums.DeliveryStatus;

public record UpdateDeliveryDTO(
        DeliveryStatus status,
        String trackingCode
) {
}
