package Alavarse.Ortega.Battery.Commerce.Service;

import Alavarse.Ortega.Battery.Commerce.Constants.AuthConstants;
import Alavarse.Ortega.Battery.Commerce.DTO.AuthenticationDTO;
import Alavarse.Ortega.Battery.Commerce.DTO.LoginResponseDTO;
import Alavarse.Ortega.Battery.Commerce.DTO.RegisterDTO;
import Alavarse.Ortega.Battery.Commerce.Entity.UserEntity;
import Alavarse.Ortega.Battery.Commerce.Repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AuthorizationService implements UserDetailsService{

    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username);
    }

    public ResponseEntity login (@RequestBody @Valid AuthenticationDTO data, AuthenticationManager authenticationManager){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((UserEntity) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));

    }

    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) throws Exception {
        if (this.repository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().build();

        verifyEmail(data.email());
        verifyPassword(data.password());

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        UserEntity newUser = new UserEntity(data.email(), encryptedPassword, data.name(), data.document(), data.role());

        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }

    private void verifyEmail(String email) throws Exception {
        Pattern pattern = Pattern.compile(AuthConstants.EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()){
            throw new Exception();
        }
    }

    private boolean containSpecialCharacters(String password) throws Exception {
        for (char character: AuthConstants.SPECIAL_CHARACTERS.toCharArray()) {
            if (!password.contains(String.valueOf(character))){
               return false;
            }
        }
        return true;
    }

    private void verifyPassword(String password) throws Exception {
        if ( password.length() < 7 && !containSpecialCharacters(password)){
            throw new Exception();
        }
    }

}
