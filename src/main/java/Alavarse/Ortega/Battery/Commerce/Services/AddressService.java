package Alavarse.Ortega.Battery.Commerce.Services;

import Alavarse.Ortega.Battery.Commerce.DTOs.AddressDTO;
import Alavarse.Ortega.Battery.Commerce.DTOs.UpdateAddressDTO;
import Alavarse.Ortega.Battery.Commerce.Entities.AddressEntity;
import Alavarse.Ortega.Battery.Commerce.Exceptions.AddressExceptions.*;
import Alavarse.Ortega.Battery.Commerce.Repositories.AddressRepository;
import org.springframework.beans.BeanUtils;
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
        verifyAddressesSize(data.userId());
        if (data.main()){
            setAddressMainFalse(data.userId());
        }
        try {
            return repository.save(new AddressEntity(data, userService.findById(data.userId())));
        } catch (Exception e){
            throw new ErrorWhileSavingAddressException();
        }
    }

    public AddressEntity setAddressMainTrue(String id){
        AddressEntity address = this.getById(id);
        setAddressMainFalse(id);
        address.setMain(true);
        return this.repository.save(address);
    }

    private void verifyAddressesSize(String id){
        List<AddressEntity> list = this.getByUser(id);
        if (list.size() >= 3){
            throw new TooMuchAddressesException();
        }
    }

    private void setAddressMainFalse(String id){
        List<AddressEntity> list = this.getByUser(id);
        list.forEach(addressEntity -> {
            addressEntity.setMain(false);
            repository.save(addressEntity);
        });
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

    public void delete(String id){
        AddressEntity address  =this.getById(id);
        try{
            this.repository.delete(address);
        } catch (Exception e){
            throw new ErrorWhileSavingAddressException();
        }
    }

    public AddressEntity patchUpdate(UpdateAddressDTO data, String addressId){
        try {
            AddressEntity foundAddress = this.getById(addressId);
            BeanUtils.copyProperties(data, foundAddress, "addressId");
            return repository.save(foundAddress);
        } catch (Exception e){
            throw new ErrorWhileSavingAddressException();
        }
    }
}
