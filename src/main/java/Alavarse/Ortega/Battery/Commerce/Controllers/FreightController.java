package Alavarse.Ortega.Battery.Commerce.Controllers;

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
    @GetMapping("/{cep}")
    public ResponseEntity<String> getFreightInfo(@PathVariable @Valid String cep){
        return this.service.getFreightInfo(cep);
    }
}
