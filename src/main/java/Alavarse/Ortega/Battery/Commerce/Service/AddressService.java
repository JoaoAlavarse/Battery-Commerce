package Alavarse.Ortega.Battery.Commerce.Service;

import Alavarse.Ortega.Battery.Commerce.DTO.AddressDTO;
import Alavarse.Ortega.Battery.Commerce.Entity.AddressEntity;
import Alavarse.Ortega.Battery.Commerce.Entity.UserEntity;
import Alavarse.Ortega.Battery.Commerce.Exceptions.UserExceptions.ErrorWhileGettingUsersException;
import Alavarse.Ortega.Battery.Commerce.Repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    @Autowired
    private AddressRepository repository;
    @Autowired
    private UserService userService;


    public AddressEntity create(AddressDTO data){
        Optional<UserEntity> optionalUser = userService.findById(data.userId());
        if (optionalUser.isEmpty()){
            throw new ErrorWhileGettingUsersException();
        }
        UserEntity foundUser = optionalUser.get();
        AddressEntity address = new AddressEntity(data.address(), data.number(), data.complement(), data.city(), data.state(), data.CEP(), foundUser);
        repository.save(address);
        return address;
    }

    public List<AddressEntity> getAll(){
        return repository.findAll();
    }

    public List<AddressEntity> getByUser(String userId){
        Optional<UserEntity> optionalUser = userService.findById(userId);
        if (optionalUser.isEmpty()){
            throw new ErrorWhileGettingUsersException();
        }
        UserEntity foundUser = optionalUser.get();
        return repository.findByUser(foundUser);
    }
}
