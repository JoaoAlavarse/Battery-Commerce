package Alavarse.Ortega.Battery.Commerce.Controller;

import Alavarse.Ortega.Battery.Commerce.DTO.BatteryDTO;
import Alavarse.Ortega.Battery.Commerce.Entity.BatteryEntity;
import Alavarse.Ortega.Battery.Commerce.Service.BatteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

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

    @PostMapping(value = "/{id}/images")
    public ResponseEntity<String> addImages(@PathVariable String id, @RequestBody List<MultipartFile> images){
        service.addImages(id, images);
        return ResponseEntity.ok("OK");
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<BatteryEntity> technicalDelete(@PathVariable String id){
        return ResponseEntity.ok().body(service.technicalDelete(id));
    }

    @GetMapping(value = "/{id}/images")
    public ResponseEntity<StreamingResponseBody> getImages(@PathVariable String id){
        return service.getImages(id);
    }
}
