package Alavarse.Ortega.Battery.Commerce.Service;

import Alavarse.Ortega.Battery.Commerce.Constants.AuthConstants;
import Alavarse.Ortega.Battery.Commerce.DTO.AuthenticationDTO;
import Alavarse.Ortega.Battery.Commerce.DTO.LoginResponseDTO;
import Alavarse.Ortega.Battery.Commerce.DTO.RegisterDTO;
import Alavarse.Ortega.Battery.Commerce.Entity.UserEntity;
import Alavarse.Ortega.Battery.Commerce.Enum.UserRole;
import Alavarse.Ortega.Battery.Commerce.Exceptions.AuthExceptions.*;
import Alavarse.Ortega.Battery.Commerce.Exceptions.UserExceptions.ErrorWhileSavingUserException;
import Alavarse.Ortega.Battery.Commerce.Repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private UserService service;
    @Autowired
    private CartService cartService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username);
    }

    public LoginResponseDTO login (@RequestBody @Valid AuthenticationDTO data, AuthenticationManager authenticationManager){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((UserEntity) auth.getPrincipal());

        return new LoginResponseDTO(token);

    }

    public UserEntity register(@RequestBody @Valid RegisterDTO data) throws RuntimeException {
        if (this.repository.findByEmail(data.email()) != null){
            throw new EmailAlreadyExistsException();
        }
        if (this.repository.findByDocument(data.document()).isPresent()){
            throw new DocumentAlreadyExistsException();
        }

        verifyEmail(data.email());
        verifyPassword(data.password());
        this.service.verifyDocument(data.document());

        try {
            String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
            UserEntity newUser = new UserEntity(data.email(), encryptedPassword, data.name(), data.document(), UserRole.USER);

            UserEntity user = this.repository.save(newUser);
            cartService.create(user.getUserId());
            return user;
        } catch (Exception e){
            throw new ErrorWhileSavingUserException();
        }
    }

    private void verifyEmail(String email) throws InvalidEmailException {
        Pattern pattern = Pattern.compile(AuthConstants.EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()){
            throw new InvalidEmailException();
        }
    }

    public static void containsSpecialCharacters(String password) throws DoesntContainSpecialCharacterException {
        boolean containsSpecialCharacter = false;

        for (char character : AuthConstants.SPECIAL_CHARACTERS.toCharArray()) {
            if (password.contains(String.valueOf(character))) {
                containsSpecialCharacter = true;
                break;
            }
        }

        if (!containsSpecialCharacter) {
            throw new DoesntContainSpecialCharacterException();
        }
    }

    private void containNumbers (String password) throws DoesntContainNumbersException{
        int count = 0;

        for (char character : password.toCharArray()) {
            if (Character.isDigit(character)) {
                count++;
            }
        }

        if (count < 5) {
            throw new DoesntContainNumbersException();
        }
    }

    private void verifyPasswordSize(String password) throws InvalidPasswordSizeException{
        if (password.length() < 12)
            throw new InvalidPasswordSizeException();
    }

    private void verifyPassword(String password) throws RuntimeException {
        containNumbers(password);
        verifyPasswordSize(password);
        containsSpecialCharacters(password);
    }

}
