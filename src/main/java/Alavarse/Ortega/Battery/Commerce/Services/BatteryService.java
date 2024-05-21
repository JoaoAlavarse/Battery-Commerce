package Alavarse.Ortega.Battery.Commerce.Services;

import Alavarse.Ortega.Battery.Commerce.DTOs.BatteryDTO;
import Alavarse.Ortega.Battery.Commerce.Entities.BatteryEntity;
import Alavarse.Ortega.Battery.Commerce.Enums.BatteryStatus;
import Alavarse.Ortega.Battery.Commerce.Exceptions.BatteryExceptions.BatteryAlreadyExistsException;
import Alavarse.Ortega.Battery.Commerce.Exceptions.BatteryExceptions.BatteryNotFoundException;
import Alavarse.Ortega.Battery.Commerce.Exceptions.BatteryExceptions.ErrorWhileGettingBatteryException;
import Alavarse.Ortega.Battery.Commerce.Exceptions.BatteryExceptions.ErrorWhileSavingBatteryException;
import Alavarse.Ortega.Battery.Commerce.Repositories.BatteryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BatteryService {
    @Autowired
    private BatteryRepository repository;

    public BatteryEntity create(BatteryDTO data){
        if (repository.findByCode(data.code()).isPresent()){
            throw new BatteryAlreadyExistsException();
        }
        try {
            return repository.save(new BatteryEntity(data.name(), data.description(), data.value(), data.quantity(), data.code()));
        } catch (Exception e){
            throw new ErrorWhileSavingBatteryException();
        }
    }

    public BatteryEntity reactiveBattery(String id){
        BatteryEntity battery = this.getById(id);
        if (!battery.getStatus().equals(BatteryStatus.INACTIVE)){
            throw new ErrorWhileSavingBatteryException();
        }
        try {
            battery.setStatus(BatteryStatus.ACTIVE);
            return repository.save(battery);
        } catch (Exception e){
            throw new ErrorWhileSavingBatteryException();
        }
    }

    public List<BatteryEntity> getByList(String[] idList){
        System.out.println("alo" + Arrays.toString(idList));
        try {
            List<BatteryEntity> data = new ArrayList<>();
            for(String id : idList){
                System.out.println(id);
                data.add((this.getById(id)));
            };
            return data;
        } catch (Exception e){
            throw new RuntimeException("NOSSA");
        }
    }

    public List<BatteryEntity> getAll(){
        try {
            return repository.findAll();
        } catch (Exception e){
            throw new ErrorWhileGettingBatteryException();
        }
    }

    public List<BatteryEntity> getAllActive(){
        try{
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

    public BatteryEntity updateQuantity (String id, Integer quantity){
        try {
            BatteryEntity battery = repository.findById(id).orElseThrow(BatteryNotFoundException::new);
            battery.setQuantity(quantity);
            return repository.save(battery);
        } catch (Exception e) {
            throw new ErrorWhileSavingBatteryException();
        }
    }
    public List<BatteryEntity> getReportData(String report) {
        return switch (report) {
            case "battery-active" -> this.repository.findAllActive();
            case "battery-inactive" -> this.repository.findAllInactive();
            case "battery-value-100" -> this.repository.findByPrice(0, 100);
            case "battery-value-250" -> this.repository.findByPrice(100, 250);
            case "battery-value-500" -> this.repository.findByPrice(250, 500);
            case "battery-value-over-500" -> this.repository.findByPrice(500, 10000);
            case "battery-quantity-100" -> this.repository.findByQuantity(0, 100);
            case "battery-quantity-250" -> this.repository.findByQuantity(100, 250);
            case "battery-quantity-500" -> this.repository.findByQuantity(250, 500);
            case "battery-quantity-over-500" -> this.repository.findByQuantity(500, 10000);
            case "battery-clear" -> this.repository.findAll();
            default -> throw new RuntimeException("VO NADA");
        };
    }

        public BatteryEntity patchUpdate (String id, BatteryDTO data){
        BatteryEntity battery = this.getById(id);
        try {
            BeanUtils.copyProperties(data, battery, "batteryId");
            battery.setStatus(BatteryStatus.ACTIVE);
            return repository.save(battery);
        } catch (Exception e){
            throw new ErrorWhileSavingBatteryException();
        }
    }
}