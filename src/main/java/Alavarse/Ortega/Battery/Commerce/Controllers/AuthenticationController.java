package Alavarse.Ortega.Battery.Commerce.Controllers;

import Alavarse.Ortega.Battery.Commerce.DTOs.Authentication.AuthenticationDTO;
import Alavarse.Ortega.Battery.Commerce.DTOs.Authentication.LoginResponseDTO;
import Alavarse.Ortega.Battery.Commerce.DTOs.Authentication.RegisterDTO;
import Alavarse.Ortega.Battery.Commerce.DTOs.Authentication.VerifyRegisterDataDTO;
import Alavarse.Ortega.Battery.Commerce.Entities.UserEntity;
import Alavarse.Ortega.Battery.Commerce.Repositories.UserRepository;
import Alavarse.Ortega.Battery.Commerce.Services.AuthorizationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<LoginResponseDTO> login (@RequestBody @Valid AuthenticationDTO data){
        return ResponseEntity.ok(this.service.login(data, authenticationManager));

    }

    @PostMapping("/register")
    public ResponseEntity<UserEntity> register(@RequestBody @Valid RegisterDTO data) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.register(data));
    }

    @GetMapping("/{email}")
    public ResponseEntity<HttpStatus> verifyUserRole(@PathVariable @Valid String email){
        return ResponseEntity.status(HttpStatus.OK).body(this.service.verifyUserRole(email));
    }

    @PostMapping("/verify")
    public ResponseEntity<HttpStatus> verifyData(@RequestBody @Valid VerifyRegisterDataDTO data){
        return ResponseEntity.status(HttpStatus.OK).body(this.service.verifyData(data));
    }
}
