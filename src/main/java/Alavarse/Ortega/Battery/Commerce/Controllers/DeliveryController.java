package Alavarse.Ortega.Battery.Commerce.Controllers;

import Alavarse.Ortega.Battery.Commerce.Entities.DeliveryEntity;
import Alavarse.Ortega.Battery.Commerce.Enums.DeliveryStatus;
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

    @PutMapping("/{deliveryId}/code/{code}")
    public ResponseEntity<DeliveryEntity> updateTrackingCode(@PathVariable String deliveryId, @PathVariable String code){
        return ResponseEntity.ok().body(this.service.updateTrackingCode(deliveryId, code));
    }

    @PutMapping("/{deliveryId}/status/{status}")
    public ResponseEntity<DeliveryEntity> updateStatus(@PathVariable String deliveryId, @PathVariable DeliveryStatus status){
        return ResponseEntity.ok().body(this.service.updateStatus(deliveryId, status));
    }

    @GetMapping("/report/{report}")
    public ResponseEntity<List<DeliveryEntity>> getReportData(@PathVariable String report){
        return ResponseEntity.ok().body(this.service.getReportData(report));
    }
}
