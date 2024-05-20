package Alavarse.Ortega.Battery.Commerce.Controllers;

import Alavarse.Ortega.Battery.Commerce.DTOs.BatteryDTO;
import Alavarse.Ortega.Battery.Commerce.Entities.BatteryEntity;
import Alavarse.Ortega.Battery.Commerce.Services.BatteryService;
import jakarta.validation.Valid;
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

    @GetMapping("/all")
    public ResponseEntity<List<BatteryEntity>> getAll(){
        return ResponseEntity.ok().body(service.getAll());
    }

    @GetMapping()
    public ResponseEntity<List<BatteryEntity>> getAllActive() { return ResponseEntity.ok().body(service.getAllActive()); }

    @PutMapping(value = "/{id}/quantity")
    public ResponseEntity<BatteryEntity> updateQuantity(@PathVariable String id, @RequestBody @Valid Integer quantity){
        return ResponseEntity.ok().body(service.updateQuantity(id, quantity));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BatteryEntity> getById(@PathVariable @Valid String id){
        return ResponseEntity.ok().body(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<BatteryEntity> create(@RequestBody @Valid BatteryDTO data){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(data));
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<BatteryEntity> technicalDelete(@PathVariable @Valid String id){
        return ResponseEntity.ok().body(service.technicalDelete(id));
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<BatteryEntity> patchUpdate(@PathVariable String id, @RequestBody @Valid BatteryDTO data){
        return ResponseEntity.ok().body(service.patchUpdate(id, data));
    }

    @PutMapping("/reactive/{id}")
    public ResponseEntity<BatteryEntity> reactiveBattery(@PathVariable String id){
        return ResponseEntity.ok().body(service.reactiveBattery(id));
    }

    @GetMapping("/list")
    public ResponseEntity<List<BatteryEntity>> getByList(@RequestBody List<String> idList){
        return  ResponseEntity.ok().body(service.getByList(idList));
    }

}
