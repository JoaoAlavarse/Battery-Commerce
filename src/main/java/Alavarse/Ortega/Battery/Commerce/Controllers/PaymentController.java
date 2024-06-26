package Alavarse.Ortega.Battery.Commerce.Controllers;

import Alavarse.Ortega.Battery.Commerce.DTOs.Payment.Card.PaymentCardRequestDTO;
import Alavarse.Ortega.Battery.Commerce.DTOs.Payment.PaymentResponseDTO;
import Alavarse.Ortega.Battery.Commerce.DTOs.Payment.Pix.PaymentPixRequestDTO;
import Alavarse.Ortega.Battery.Commerce.DTOs.Payment.Ticket.PaymentTicketRequestDTO;
import Alavarse.Ortega.Battery.Commerce.Services.PaymentService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("payment")
public class PaymentController {
    @Autowired
    private PaymentService service;

    @PostMapping("/pix")
    public ResponseEntity<ObjectNode> createPix(@RequestBody @Valid PaymentPixRequestDTO pixData){
        return this.service.createPix(pixData);
    }

    @PostMapping("/card")
    public ResponseEntity<ObjectNode> createCard(@RequestBody @Valid PaymentCardRequestDTO cardData){
        return this.service.createCard(cardData);
    }

    @PostMapping("/ticket")
    public ResponseEntity<ObjectNode> createTicket(@RequestBody @Valid PaymentTicketRequestDTO ticketData){
        return this.service.createTicket(ticketData);
    }

    @PutMapping("/{idpk}/card/{id}")
    public ResponseEntity<String> payCard(@PathVariable String idpk, @PathVariable String id){
        return this.service.payCard(idpk, id);
    }

    @PostMapping("/receive")
    public void teste(@RequestBody Object dada){
        System.out.println(dada.toString());
    }
}
