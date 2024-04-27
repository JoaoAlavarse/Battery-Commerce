package Alavarse.Ortega.Battery.Commerce.Controllers;

import Alavarse.Ortega.Battery.Commerce.DTOs.DiscountDTO;
import Alavarse.Ortega.Battery.Commerce.DTOs.PromotionDTO;
import Alavarse.Ortega.Battery.Commerce.Entities.PromotionEntity;
import Alavarse.Ortega.Battery.Commerce.Services.PromotionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("promotion")
public class PromotionController {
    @Autowired
    private PromotionService service;

    @PostMapping
    public ResponseEntity<PromotionEntity> create(@RequestBody @Valid PromotionDTO data){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PromotionEntity> delete(@PathVariable String id){
        return ResponseEntity.ok().body(service.technicalDelete(id));
    }

    @GetMapping("/{code}/code")
    public ResponseEntity<PromotionEntity> getByCode(@PathVariable String code){
        return ResponseEntity.ok().body(service.getByCode(code));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PromotionEntity> getById(@PathVariable String id){
        return ResponseEntity.ok().body(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<PromotionEntity>> getAll(){
        return ResponseEntity.ok().body(service.getAll());
    }

    @PostMapping("/{code}/total")
    public ResponseEntity<BigDecimal> getTotalValue(@PathVariable String code, @RequestBody @Valid DiscountDTO data){
        return ResponseEntity.ok().body(service.getDiscountValue(code, data));
    }

    @PutMapping("/reactive/{id}")
    public ResponseEntity<PromotionEntity> reactivePromotion(@PathVariable String id, @RequestBody @Valid PromotionDTO data){
        return ResponseEntity.ok().body(service.reactivePromotion(id, data));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PromotionEntity> patchUpdate(@PathVariable String id, @RequestBody PromotionDTO data){
        return ResponseEntity.ok().body(service.patchUpdate(id, data));
    }

}
