package Alavarse.Ortega.Battery.Commerce.Controllers;

import Alavarse.Ortega.Battery.Commerce.DTOs.Payment.Card.PaymentCardRequestDTO;
import Alavarse.Ortega.Battery.Commerce.DTOs.Payment.Pix.PaymentPixRequestDTO;
import Alavarse.Ortega.Battery.Commerce.DTOs.Payment.Ticket.PaymentTicketRequestDTO;
import Alavarse.Ortega.Battery.Commerce.Services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("payment")
public class PaymentController {
    @Autowired
    private PaymentService service;

    @PostMapping("/pix")
    public ResponseEntity<String> createPix(@RequestBody PaymentPixRequestDTO pixData){
        return this.service.createPix(pixData);
    }

    @PostMapping("/card")
    public ResponseEntity<String> createCard(@RequestBody PaymentCardRequestDTO cardData){
        return this.service.createCard(cardData);
    }

    @PostMapping("/ticket")
    public ResponseEntity<String> createTicket(@RequestBody PaymentTicketRequestDTO ticketData){
        return this.service.createTicket(ticketData);
    }
}
