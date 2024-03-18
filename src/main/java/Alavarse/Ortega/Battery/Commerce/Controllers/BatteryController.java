package Alavarse.Ortega.Battery.Commerce.Controllers;

import Alavarse.Ortega.Battery.Commerce.DTOs.BatteryDTO;
import Alavarse.Ortega.Battery.Commerce.Entities.BatteryEntity;
import Alavarse.Ortega.Battery.Commerce.Services.BatteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("battery")
public class BatteryController {
    @Autowired
    private BatteryService service;

    @GetMapping
    public ResponseEntity<List<BatteryEntity>> getAll(){
        return ResponseEntity.ok().body(service.getAllActive());
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BatteryEntity> update(@PathVariable String id, @RequestBody BatteryDTO data){
        return ResponseEntity.ok().body(service.update(id, data));
    }

    @PutMapping(value = "/{id}/quantity")
    public ResponseEntity<BatteryEntity> updateQuantity(@PathVariable String id, @RequestBody Integer quantity){
        return ResponseEntity.ok().body(service.updateQuantity(id, quantity));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BatteryEntity> getById(@PathVariable String id){
        return ResponseEntity.ok().body(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<BatteryEntity> create(@RequestBody BatteryDTO data){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(data));
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<BatteryEntity> technicalDelete(@PathVariable String id){
        return ResponseEntity.ok().body(service.technicalDelete(id));
    }

}
