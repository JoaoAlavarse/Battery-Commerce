package Alavarse.Ortega.Battery.Commerce.Service;

import Alavarse.Ortega.Battery.Commerce.DTO.AddressDTO;
import Alavarse.Ortega.Battery.Commerce.DTO.UpdateAddressDTO;
import Alavarse.Ortega.Battery.Commerce.Entity.AddressEntity;
import Alavarse.Ortega.Battery.Commerce.Entity.UserEntity;
import Alavarse.Ortega.Battery.Commerce.Exceptions.AddressExceptions.AddressNotFoundException;
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
        UserEntity foundUser = verifyUser(data.userId());
        AddressEntity address = new AddressEntity(data, foundUser);
        repository.save(address);
        return address;
    }

    public List<AddressEntity> getAll(){
        return repository.findAll();
    }

    public List<AddressEntity> getByUser(String userId){
        UserEntity foundUser = verifyUser(userId);
        return repository.findByUser(foundUser);
    }

    public AddressEntity update(UpdateAddressDTO data, String addressId){
        Optional<AddressEntity> foundAddress = repository.findById(addressId);
        if (foundAddress.isEmpty()){
            throw new AddressNotFoundException();
        }
        AddressEntity updatedAddress = foundAddress.get();
        updatedAddress.setAddress(data.address());
        updatedAddress.setCity(data.city());
        updatedAddress.setCEP(data.CEP());
        updatedAddress.setState(data.state());
        updatedAddress.setComplement(data.complement());
        updatedAddress.setNumber(data.number());
        return repository.save(updatedAddress);
    }

    public UserEntity verifyUser(String userId){
        Optional<UserEntity> optionalUser = userService.findById(userId);
        if (optionalUser.isEmpty()){
            throw new ErrorWhileGettingUsersException();
        }
        return optionalUser.get();
    }
}
