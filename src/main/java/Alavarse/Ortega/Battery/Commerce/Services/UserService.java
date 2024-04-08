package Alavarse.Ortega.Battery.Commerce.Services;

import Alavarse.Ortega.Battery.Commerce.DTOs.UpdateUserDTO;
import Alavarse.Ortega.Battery.Commerce.Entities.UserEntity;
import Alavarse.Ortega.Battery.Commerce.Enums.UserRole;
import Alavarse.Ortega.Battery.Commerce.Enums.UserStatus;
import Alavarse.Ortega.Battery.Commerce.Exceptions.UserExceptions.*;
import Alavarse.Ortega.Battery.Commerce.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public List<UserEntity> findAll() throws ErrorWhileGettingUsersException{
        try {
            return repository.findAllActiveUsers();
        } catch (Exception e){
            throw new ErrorWhileGettingUsersException();
        }
    }

    public UserDetails getByEmail(String email){
        try {
            return repository.findByEmail(email);
        } catch (Exception e){
            throw new UserNotFoundException();
        }
    }

    public UserEntity findById(String id){
            return repository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public List<UserEntity> findByRole(String role) throws ErrorWhileGettingUsersException{
       try {
            return repository.findAllByRoleAndActive(role);
       } catch (Exception e){
           throw new ErrorWhileGettingUsersException();
       }
    }

    public UserEntity updateUser(String id, UpdateUserDTO data){
        try {
            UserEntity user = repository.findById(id).orElseThrow(UserNotFoundException::new);
            String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
            user.setName(data.name());
            user.setPassword(encryptedPassword);
            user.setStatus(data.status());
            return repository.save(user);
        } catch (Exception e){
            throw new ErrorWhileSavingUserException();
        }
    }

    public UserEntity turnIntoAdmin(String id){
        try {
            UserEntity user = repository.findById(id).orElseThrow(UserNotFoundException::new);
            user.setRole(UserRole.ADMIN);
            return repository.save(user);
        } catch (Exception e){
            throw new ErrorWhileSavingUserException();
        }
    }

    public UserEntity technicalDelete(String id) throws ErrorWhileSavingUserException{
        try {
            UserEntity user = repository.findById(id).orElseThrow(UserNotFoundException::new);
            user.setStatus(UserStatus.INACTIVE);
            return repository.save(user);
        } catch (Exception e){
            throw new ErrorWhileSavingUserException();
        }
    }

    public void verifyDocument(String document) throws InvalidDocumentException{
        verifyDocumentSize(document);
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += Character.getNumericValue(document.charAt(i)) * (10 - i);
        }

        int rest = sum % 11;
        int firstDigit = (rest < 2) ? 0 : (11 - rest);

        if (firstDigit != Character.getNumericValue(document.charAt(9))) {
            throw new InvalidDocumentException();
        }

        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += Character.getNumericValue(document.charAt(i)) * (11 - i);
        }

        rest = sum % 11;
        int secondDigit = (rest < 2) ? 0 : (11 - rest);

        if (secondDigit != Character.getNumericValue(document.charAt(10))){
            throw new InvalidDocumentException();
        }
    }

    private void verifyDocumentSize(String document) throws InvalidDocumentSizeException{
        if (document.length() != 11) {
            throw new InvalidDocumentSizeException();
        }
    }


}
