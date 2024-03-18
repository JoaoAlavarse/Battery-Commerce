package Alavarse.Ortega.Battery.Commerce.Controller;

import Alavarse.Ortega.Battery.Commerce.DTO.AddressDTO;
import Alavarse.Ortega.Battery.Commerce.DTO.UpdateAddressDTO;
import Alavarse.Ortega.Battery.Commerce.Entity.AddressEntity;
import Alavarse.Ortega.Battery.Commerce.Service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressService service;
    @PostMapping
    public ResponseEntity<AddressEntity> create(@RequestBody AddressDTO data){
        return ResponseEntity.ok().body(service.create(data));
    }

    @GetMapping
    public ResponseEntity<List<AddressEntity>> getAll(){
        return ResponseEntity.ok().body(service.getAll());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AddressEntity>> getByUser(@PathVariable String userId){
        return ResponseEntity.ok().body(service.getByUser(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressEntity> update(@PathVariable String id, @RequestBody UpdateAddressDTO data){
        return ResponseEntity.ok().body(service.update(data, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressEntity> getById(@PathVariable String id){
        return ResponseEntity.ok().body(service.getById(id));
    }
}
