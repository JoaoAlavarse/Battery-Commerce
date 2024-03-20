package Alavarse.Ortega.Battery.Commerce.Controllers;

import Alavarse.Ortega.Battery.Commerce.Entities.CartEntity;
import Alavarse.Ortega.Battery.Commerce.Services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService service;

    @GetMapping("/{id}")
    public ResponseEntity<CartEntity> getById(@PathVariable String id){
        return ResponseEntity.ok().body(this.service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<CartEntity>> getAll(){
        return ResponseEntity.ok().body(this.service.getAll());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<CartEntity> getByUser(@PathVariable String id){
        return ResponseEntity.ok().body(this.service.getByUser(id));
    }

    @DeleteMapping("/{id}/battery/{batteryId}")
    public ResponseEntity<CartEntity> deleteBattery(@PathVariable String id, @PathVariable String batteryId){
        return ResponseEntity.ok().body(this.service.removeBatteries(id, batteryId));
    }

    @DeleteMapping("/{id}/promotion")
    public ResponseEntity<CartEntity> deletePromotion(@PathVariable String id){
        return ResponseEntity.ok().body(this.service.removePromotion(id));
    }

    @PutMapping("/{id}/battery/{batteryId}/quantity/{quantity}")
    public ResponseEntity<CartEntity> addBattery(@PathVariable String id, @PathVariable String batteryId, @PathVariable int quantity){
        return ResponseEntity.ok().body(this.service.addBatteries(id, batteryId, quantity));
    }

    @PutMapping("/{id}/promotion/{promotionCode}")
    public ResponseEntity<CartEntity> addPromotion(@PathVariable String id, @PathVariable String promotionCode){
        return ResponseEntity.ok().body(this.service.addPromotion(id, promotionCode));
    }
}
