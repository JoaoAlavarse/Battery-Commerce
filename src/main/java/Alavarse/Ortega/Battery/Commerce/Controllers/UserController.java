package Alavarse.Ortega.Battery.Commerce.Controllers;

import Alavarse.Ortega.Battery.Commerce.DTOs.User.UpdateUserDTO;
import Alavarse.Ortega.Battery.Commerce.Entities.UserEntity;
import Alavarse.Ortega.Battery.Commerce.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserEntity>> getByRole(@PathVariable String role){
        return ResponseEntity.ok().body(this.service.findByRole(role));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserEntity> getByEmail(@PathVariable String email){
        return ResponseEntity.ok().body(this.service.getByEmail(email));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserEntity> getById(@PathVariable String id){
        return ResponseEntity.ok().body(this.service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserEntity>> getAll(){
        return ResponseEntity.ok().body(this.service.findAll());
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<UserEntity> patchUpdate(@PathVariable String id, @RequestBody @Valid UpdateUserDTO data){
        return ResponseEntity.ok().body(this.service.patchUpdate(id, data));
    }

    @DeleteMapping(value = "/{id}/{password}")
    public ResponseEntity<UserEntity> technicalDelete(@PathVariable String id, @PathVariable String password){
        return ResponseEntity.ok().body(this.service.technicalDelete(id, password));
    }

    @PutMapping(value = "/changeRole/{id}/{role}/{loggedUserId}")
    public ResponseEntity<UserEntity> changeRole(@PathVariable String id, @PathVariable String role, @PathVariable String loggedUserId){
        return ResponseEntity.ok().body(this.service.changeRole(id, role, loggedUserId));
    }

    @GetMapping("/report/{report}")
    public ResponseEntity<List<UserEntity>> getReportData(@PathVariable String report){
        return ResponseEntity.ok().body(this.service.getReportData(report));
    }
}
