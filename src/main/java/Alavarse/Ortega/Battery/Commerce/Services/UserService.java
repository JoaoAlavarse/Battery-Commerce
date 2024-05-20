package Alavarse.Ortega.Battery.Commerce.Services;

import Alavarse.Ortega.Battery.Commerce.Constants.AuthConstants;
import Alavarse.Ortega.Battery.Commerce.DTOs.UpdateUserDTO;
import Alavarse.Ortega.Battery.Commerce.Entities.UserEntity;
import Alavarse.Ortega.Battery.Commerce.Enums.UserRole;
import Alavarse.Ortega.Battery.Commerce.Enums.UserStatus;
import Alavarse.Ortega.Battery.Commerce.Exceptions.AuthExceptions.*;
import Alavarse.Ortega.Battery.Commerce.Exceptions.UserExceptions.*;
import Alavarse.Ortega.Battery.Commerce.Repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public UserEntity getByEmail(String email){
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

    public UserEntity patchUpdate(String id, UpdateUserDTO data){
        UserEntity user = this.findById(id);
        verifyEmail(data.email());
        try{
            BeanUtils.copyProperties(data, user, "userId");
            user.setStatus(UserStatus.ACTIVE);
            return repository.save(user);
        } catch (Exception e){
            throw new ErrorWhileSavingUserException();
        }
    }

    public List<UserEntity> getReportData(String report){
        return switch (report) {
            case "user-active" -> this.repository.findAllActiveUsers();
            case "user-inactive" -> this.repository.findAllInactive();
            case "user-user" -> this.repository.findAllUsers();
            case "user-admin" -> this.repository.findAllAdmins();
            case "user-clear" -> this.repository.findAll();
            default -> throw new ErrorGettingUserReportData();
        };
    }

    public UserEntity changeRole(String id, String role, String loggedUserId){
        UserEntity user = this.findById(id);
        UserEntity loggedUser = this.findById(loggedUserId);
        if (user.equals(loggedUser)){
            throw new SameUserRoleChangeException();
        }
        if (user.getRole().equals(UserRole.valueOf(role))){
            throw new ErrorWhileChangingRolesException();
        }
        try {
            user.setRole(UserRole.valueOf(role));
            return repository.save(user);
        } catch (Exception e){
            throw new ErrorWhileSavingUserException();
        }
    }

    public void verifyEmail(String email) throws InvalidEmailException {
        Pattern pattern = Pattern.compile(AuthConstants.EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()){
            throw new InvalidEmailException();
        }
    }

    public UserEntity technicalDelete(String id, String password) throws ErrorWhileSavingUserException{
        UserEntity user = repository.findById(id).orElseThrow(UserNotFoundException::new);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(password, user.getPassword())){
            throw new InvalidPasswordException();
        }
        try {
            user.setStatus(UserStatus.INACTIVE);
            return repository.save(user);
        } catch (Exception e){
            throw new ErrorWhileSavingUserException();
        }
    }

    public String verifyDocument(String document) throws InvalidDocumentException{
        verifyDocumentSize(document);
        document = document.replaceAll("[^0-9]", "");

        if (this.repository.findByDocument(document).isPresent()){
            throw new DocumentAlreadyExistsException();
        }

        ArrayList<String> invalidDocuments = new ArrayList<>(Arrays.asList("11111111111", "22222222222",
                "33333333333", "44444444444", "55555555555",
                "66666666666", "77777777777", "88888888888",
                "99999999999", "00000000000"));

        if (invalidDocuments.contains(document)){
            throw new InvalidDocumentException();
        }


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

        return document;
    }

    private void verifyDocumentSize(String document) throws InvalidDocumentSizeException{
        if (document.length() != 14) {
            throw new InvalidDocumentSizeException();
        }
    }


}
