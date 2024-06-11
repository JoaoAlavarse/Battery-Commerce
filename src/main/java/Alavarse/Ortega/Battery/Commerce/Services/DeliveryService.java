package Alavarse.Ortega.Battery.Commerce.Services;

import Alavarse.Ortega.Battery.Commerce.DTOs.UpdateDeliveryDTO;
import Alavarse.Ortega.Battery.Commerce.Entities.DeliveryEntity;
import Alavarse.Ortega.Battery.Commerce.Entities.SaleEntity;
import Alavarse.Ortega.Battery.Commerce.Entities.UserEntity;
import Alavarse.Ortega.Battery.Commerce.Repositories.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryService {
    @Autowired
    private DeliveryRepository repository;
    @Autowired
    private UserService userService;

    public void create(String address, String number, String neighborhood, String complement, String city, String state, String CEP, SaleEntity sale, UserEntity user){
            this.repository.save(new DeliveryEntity(address, number, neighborhood, complement, city, state, CEP, sale, user));
    }

    public List<DeliveryEntity> getByUser(String userId){
        return this.repository.findByUser(this.userService.findById(userId));
    }

    public List<DeliveryEntity> getAll(){
        return this.repository.findAll();
    }

    public DeliveryEntity getById(String deliveryId){
        return this.repository.findById(deliveryId).orElseThrow(RuntimeException::new);
    }

    public DeliveryEntity update(String deliveryId, UpdateDeliveryDTO data){
        DeliveryEntity delivery = this.getById(deliveryId);
        delivery.setStatus(data.status());
        delivery.setTrackingCode(data.trackingCode());
        return repository.save(delivery);
    }
}
