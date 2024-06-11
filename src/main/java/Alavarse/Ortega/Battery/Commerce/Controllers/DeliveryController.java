package Alavarse.Ortega.Battery.Commerce.Controllers;

import Alavarse.Ortega.Battery.Commerce.DTOs.UpdateDeliveryDTO;
import Alavarse.Ortega.Battery.Commerce.Entities.DeliveryEntity;
import Alavarse.Ortega.Battery.Commerce.Services.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("delivery")
public class DeliveryController {
    @Autowired
    private DeliveryService service;

    @GetMapping
    public ResponseEntity<List<DeliveryEntity>> getAll(){
        return ResponseEntity.ok().body(this.service.getAll());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DeliveryEntity>> getByUser(@PathVariable String userId){
        return ResponseEntity.ok().body(this.service.getByUser(userId));
    }

    @PutMapping("/{deliveryId}")
    public ResponseEntity<DeliveryEntity> update(@PathVariable String deliveryId, @RequestBody UpdateDeliveryDTO data){
        return ResponseEntity.ok().body(this.service.update(deliveryId, data));
    }
}
