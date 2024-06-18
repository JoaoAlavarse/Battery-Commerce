package Alavarse.Ortega.Battery.Commerce.DTOs.Payment.Ticket;

import Alavarse.Ortega.Battery.Commerce.DTOs.Sale.SaleDTO;

public record PaymentTicketRequestDTO(
    SaleDTO saleData
) {
}
