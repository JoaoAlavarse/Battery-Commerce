package Alavarse.Ortega.Battery.Commerce.Controllers;

import Alavarse.Ortega.Battery.Commerce.DTOs.Freight.FreightResponseDTO;
import Alavarse.Ortega.Battery.Commerce.Services.FreightService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("freight")
public class FreightController {
    @Autowired
    private FreightService service;
    @GetMapping("/{cep}/{quantity}")
    public ResponseEntity<FreightResponseDTO> getFreightInfo(@PathVariable @Valid String cep, @PathVariable int quantity){
        return ResponseEntity.ok().body(this.service.getFreightInfo(cep, quantity));
    }
}
