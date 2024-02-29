package Alavarse.Ortega.Battery.Commerce.Service;

import Alavarse.Ortega.Battery.Commerce.DTO.UpdateUserDTO;
import Alavarse.Ortega.Battery.Commerce.Entity.UserEntity;
import Alavarse.Ortega.Battery.Commerce.Enum.UserStatus;
import Alavarse.Ortega.Battery.Commerce.Exceptions.UserExceptions.ErrorWhileGettingUsersException;
import Alavarse.Ortega.Battery.Commerce.Exceptions.UserExceptions.InvalidDocumentException;
import Alavarse.Ortega.Battery.Commerce.Exceptions.UserExceptions.InvalidDocumentSizeException;
import Alavarse.Ortega.Battery.Commerce.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<UserEntity> findById(String id){
        try {
            return repository.findById(id);
        } catch (Exception e){
            throw new ErrorWhileGettingUsersException();
        }
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
            Optional<UserEntity> user = repository.findById(id);
            if (user.isPresent()){
                UserEntity updatedUser = user.get();
                updatedUser.setName(data.name());
                updatedUser.setPassword(data.password());
                updatedUser.setStatus(data.status());
                return repository.save(updatedUser);
            }
            throw new ErrorWhileGettingUsersException();
        } catch (Exception e){
            throw new ErrorWhileGettingUsersException();
        }
    }

    public UserEntity technicalDelete(String id) throws ErrorWhileGettingUsersException{
        try {
            Optional<UserEntity> user = repository.findById(id);
            if (user.isPresent()){
                UserEntity newUser = user.get();
                newUser.setStatus(UserStatus.INACTIVE);
                return repository.save(newUser);
            }
            throw new ErrorWhileGettingUsersException();
        } catch (Exception e){
            throw new ErrorWhileGettingUsersException();
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
