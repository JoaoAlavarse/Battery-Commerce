package Alavarse.Ortega.Battery.Commerce.Controllers;

import Alavarse.Ortega.Battery.Commerce.DTOs.DiscountDTO;
import Alavarse.Ortega.Battery.Commerce.DTOs.PromotionDTO;
import Alavarse.Ortega.Battery.Commerce.Entities.PromotionEntity;
import Alavarse.Ortega.Battery.Commerce.Services.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("promotion")
public class PromotionController {
    @Autowired
    private PromotionService service;

    @PostMapping
    public ResponseEntity<PromotionEntity> create(@RequestBody PromotionDTO data){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(data));
    }

    @DeleteMapping("/{code}/code")
    public ResponseEntity<PromotionEntity> delete(@PathVariable String code){
        return ResponseEntity.ok().body(service.technicalDelete(code));
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
    public ResponseEntity<BigDecimal> getTotalValue(@PathVariable String code, @RequestBody DiscountDTO data){
        return ResponseEntity.ok().body(service.getDiscountValue(code, data));
    }

    @PostMapping("/{code}/reactive")
    public ResponseEntity<PromotionEntity> reactivePromotion(@PathVariable String code, @RequestBody LocalDate date){
        return ResponseEntity.ok().body(service.reactivePromotion(code, date));
    }

}