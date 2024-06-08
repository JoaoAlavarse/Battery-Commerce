package Alavarse.Ortega.Battery.Commerce.Controllers;

import Alavarse.Ortega.Battery.Commerce.DTOs.SaleDTO;
import Alavarse.Ortega.Battery.Commerce.Entities.SaleEntity;
import Alavarse.Ortega.Battery.Commerce.Services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sale")
public class SaleController {
    @Autowired
    private SaleService service;

    @PostMapping
    public ResponseEntity<SaleEntity> create(@RequestBody SaleDTO data){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.create(data));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SaleEntity>> getByUser(@PathVariable String userId){
        return ResponseEntity.ok().body(this.service.getByUser(userId));
    }

}
