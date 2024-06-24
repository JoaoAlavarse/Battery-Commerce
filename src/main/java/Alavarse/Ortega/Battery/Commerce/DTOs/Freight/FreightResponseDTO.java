package Alavarse.Ortega.Battery.Commerce.DTOs.Freight;

import java.math.BigDecimal;

public record FreightResponseDTO(
        BigDecimal totalFreightCost,
        Integer estimateDays
) {
}
