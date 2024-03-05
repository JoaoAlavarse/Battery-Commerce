package Alavarse.Ortega.Battery.Commerce.Controller;

import Alavarse.Ortega.Battery.Commerce.DTO.UpdateUserDTO;
import Alavarse.Ortega.Battery.Commerce.Entity.UserEntity;
import Alavarse.Ortega.Battery.Commerce.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserEntity>> getByRole(@PathVariable String role){
        return ResponseEntity.ok().body(this.service.findByRole(role));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserEntity> getById(@PathVariable String id){
        return ResponseEntity.ok().body(this.service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserEntity>> getAll(){
        return ResponseEntity.ok().body(this.service.findAll());
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserEntity> update(@PathVariable String id, @RequestBody UpdateUserDTO data){
        return ResponseEntity.ok().body(this.service.updateUser(id, data));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UserEntity> technicalDelete(@PathVariable String id){
        return ResponseEntity.ok().body(this.service.technicalDelete(id));
    }
}
