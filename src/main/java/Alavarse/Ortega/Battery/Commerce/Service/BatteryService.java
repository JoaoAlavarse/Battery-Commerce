package Alavarse.Ortega.Battery.Commerce.Service;

import Alavarse.Ortega.Battery.Commerce.DTO.BatteryDTO;
import Alavarse.Ortega.Battery.Commerce.Entity.BatteryEntity;
import Alavarse.Ortega.Battery.Commerce.Enum.BatteryStatus;
import Alavarse.Ortega.Battery.Commerce.Exceptions.BatteryExceptions.BatteryNotFoundException;
import Alavarse.Ortega.Battery.Commerce.Exceptions.BatteryExceptions.ErrorWhileGettingBatteryException;
import Alavarse.Ortega.Battery.Commerce.Exceptions.BatteryExceptions.ErrorWhileSavingBatteryException;
import Alavarse.Ortega.Battery.Commerce.Repository.BatteryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BatteryService {
    @Autowired
    private BatteryRepository repository;

    public BatteryEntity create(BatteryDTO data){
        try {
            return repository.save(new BatteryEntity(data.name(), data.description(), data.value(), data.quantity()));
        } catch (Exception e){
            throw new ErrorWhileSavingBatteryException();
        }
    }

    public List<BatteryEntity> getAllActive(){
        try {
            return repository.findAllActive();
        } catch (Exception e){
            throw new ErrorWhileGettingBatteryException();
        }
    }

    public BatteryEntity getById(String id){
        return repository.findById(id).orElseThrow(BatteryNotFoundException::new);
    }

    public BatteryEntity technicalDelete(String id){
        try {
            BatteryEntity battery = repository.findById(id).orElseThrow(BatteryNotFoundException::new);
            battery.setStatus(BatteryStatus.INACTIVE);
            return repository.save(battery);
        } catch (Exception e){
            throw new ErrorWhileSavingBatteryException();
        }
    }



    public BatteryEntity update(String id, BatteryDTO data){
        try {
            BatteryEntity battery = repository.findById(id).orElseThrow(BatteryNotFoundException::new);
            battery.setName(data.name());
            battery.setDescription(data.description());
            battery.setStatus(data.status());
            battery.setValue(data.value());
            return repository.save(battery);
        } catch (Exception e){
            throw new ErrorWhileSavingBatteryException();
        }
    }

    public BatteryEntity updateQuantity (String id, Integer quantity){
        try {
            BatteryEntity battery = repository.findById(id).orElseThrow(BatteryNotFoundException::new);
            battery.setQuantity(battery.getQuantity() - quantity);
            return repository.save(battery);
        } catch (Exception e){
            throw new ErrorWhileSavingBatteryException();
        }
    }
}
