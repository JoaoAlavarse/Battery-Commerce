package Alavarse.Ortega.Battery.Commerce.Service;

import Alavarse.Ortega.Battery.Commerce.DTO.AddressDTO;
import Alavarse.Ortega.Battery.Commerce.DTO.UpdateAddressDTO;
import Alavarse.Ortega.Battery.Commerce.Entity.AddressEntity;
import Alavarse.Ortega.Battery.Commerce.Exceptions.AddressExceptions.AddressNotFoundException;
import Alavarse.Ortega.Battery.Commerce.Exceptions.AddressExceptions.ErrorWhileGettingAddressException;
import Alavarse.Ortega.Battery.Commerce.Exceptions.AddressExceptions.ErrorWhileSavingAddressException;
import Alavarse.Ortega.Battery.Commerce.Repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    @Autowired
    private AddressRepository repository;
    @Autowired
    private UserService userService;

    public AddressEntity create(AddressDTO data){
        try {
            return repository.save(new AddressEntity(data, userService.findById(data.userId())));
        } catch (Exception e){
            throw new ErrorWhileSavingAddressException();
        }
    }

    public AddressEntity getById(String id){
        try {
            return repository.findById(id).orElseThrow(AddressNotFoundException::new);
        } catch (Exception e){
            throw new ErrorWhileGettingAddressException();
        }
    }

    public List<AddressEntity> getAll(){
        try {
            return repository.findAll();
        } catch (Exception e){
            throw new ErrorWhileGettingAddressException();
        }
    }

    public List<AddressEntity> getByUser(String userId){
        try {
            return repository.findByUser(userService.findById(userId));
        } catch (Exception e){
            throw new ErrorWhileGettingAddressException();
        }
    }

    public AddressEntity update(UpdateAddressDTO data, String addressId){
        try {
            AddressEntity foundAddress = repository.findById(addressId).orElseThrow(AddressNotFoundException::new);
            foundAddress.setAddress(data.address());
            foundAddress.setCity(data.city());
            foundAddress.setCEP(data.CEP());
            foundAddress.setState(data.state());
            foundAddress.setComplement(data.complement());
            foundAddress.setNumber(data.number());
            return repository.save(foundAddress);
        } catch (Exception e){
            throw new ErrorWhileSavingAddressException();
        }
    }
}
