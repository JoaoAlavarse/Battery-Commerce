package Alavarse.Ortega.Battery.Commerce.Service;

import Alavarse.Ortega.Battery.Commerce.DTO.BatteryDTO;
import Alavarse.Ortega.Battery.Commerce.Entity.BatteryEntity;
import Alavarse.Ortega.Battery.Commerce.Entity.ImageEntity;
import Alavarse.Ortega.Battery.Commerce.Enum.BatteryStatus;
import Alavarse.Ortega.Battery.Commerce.Repository.BatteryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
            e.printStackTrace();
        }
        return null;
    }

    public List<BatteryEntity> getAllActive(){
        return repository.findAllActive();
    }

    public BatteryEntity getById(String id){
        return repository.findById(id).orElseThrow(RuntimeException::new);
    }

    public BatteryEntity technicalDelete(String id){
        Optional<BatteryEntity> optional = repository.findById(id);
        if (optional.isEmpty()){
            throw new RuntimeException();
        }
        BatteryEntity battery = optional.get();
        battery.setStatus(BatteryStatus.INACTIVE);
        return repository.save(battery);
    }

    public void addImages(String id, List<MultipartFile> images){
        BatteryEntity battery = repository.findById(id).orElseThrow(RuntimeException::new);

        for (MultipartFile image : images){
            try {
                ImageEntity imageEntity = new ImageEntity();
                imageEntity.setBytes(image.getBytes());
                imageEntity.setBattery(battery);

                battery.getImages().add(imageEntity);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
         repository.save(battery);
    }

    public BatteryEntity update(String id, BatteryDTO data){
        BatteryEntity battery = repository.findById(id).orElseThrow(RuntimeException::new);
        battery.setName(data.name());
        battery.setDescription(data.description());
        battery.setStatus(data.status());
        battery.setValue(data.value());
        return repository.save(battery);
    }

    public BatteryEntity updateQuantity (String id, Integer quantity){
        BatteryEntity battery = repository.findById(id).orElseThrow(RuntimeException::new);
        battery.setQuantity(battery.getQuantity() + quantity);
        return repository.save(battery);
    }
}
