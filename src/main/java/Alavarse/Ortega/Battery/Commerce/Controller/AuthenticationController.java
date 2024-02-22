package Alavarse.Ortega.Battery.Commerce.Controller;

import Alavarse.Ortega.Battery.Commerce.DTO.AuthenticationDTO;
import Alavarse.Ortega.Battery.Commerce.DTO.RegisterDTO;
import Alavarse.Ortega.Battery.Commerce.Entity.UserEntity;
import Alavarse.Ortega.Battery.Commerce.Repository.UserRepository;
import Alavarse.Ortega.Battery.Commerce.Service.AuthorizationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthorizationService service;

    @Autowired
    private UserRepository repository;

    @PostMapping("/login")
    public ResponseEntity login (@RequestBody @Valid AuthenticationDTO data){
        return ResponseEntity.ok(this.service.login(data, authenticationManager));

    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.register(data));
    }

    @GetMapping
    public List<UserEntity> teste(){
        return  repository.findAll();
    }
}
