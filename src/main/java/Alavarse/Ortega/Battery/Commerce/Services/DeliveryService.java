package Alavarse.Ortega.Battery.Commerce.Services;

import Alavarse.Ortega.Battery.Commerce.Entities.DeliveryEntity;
import Alavarse.Ortega.Battery.Commerce.Entities.SaleEntity;
import Alavarse.Ortega.Battery.Commerce.Entities.UserEntity;
import Alavarse.Ortega.Battery.Commerce.Enums.DeliveryStatus;
import Alavarse.Ortega.Battery.Commerce.Exceptions.DeliveryExceptions.*;
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

    public void create(String address, String number, String neighborhood, String complement, String city, String state, String CEP, SaleEntity sale, UserEntity user) {
        try {
            this.repository.save(new DeliveryEntity(address, number, neighborhood, complement, city, state, CEP, sale, user));
        } catch (Exception e) {
            throw new ErrorWhileSavingDeliveryException();
        }
    }

    public List<DeliveryEntity> getByUser(String userId) {
        try {
            return this.repository.findByUser(this.userService.findById(userId));
        } catch (Exception e) {
            throw new ErrorWhileGettingDeliveriesException();
        }
    }

    public List<DeliveryEntity> getAll() {
        try {
            return this.repository.findAll();
        } catch (Exception e) {
            throw new ErrorWhileGettingDeliveriesException();
        }
    }

    public DeliveryEntity getById(String deliveryId) {
        return this.repository.findById(deliveryId).orElseThrow(ErrorWhileGettingDeliveriesException::new);
    }

    public DeliveryEntity updateTrackingCode(String deliveryId, String code) {
        DeliveryEntity delivery = this.getById(deliveryId);
        try {
            delivery.setTrackingCode(code);
            return repository.save(delivery);
        } catch (Exception e){
            throw new ErrorWhileSavingDeliveryException();
        }
    }

    public DeliveryEntity updateStatus(String deliveryId, DeliveryStatus status){
        DeliveryEntity delivery = this.getById(deliveryId);
        if (delivery.getStatus().equals(DeliveryStatus.ENTREGUE)){
            throw new ChangeFinalizedDeliveryStatusException();
        }
        if (delivery.getStatus().equals(status)){
            throw new SameStatusException();
        }
        if (delivery.getStatus().equals(DeliveryStatus.PREPARANDO) && status.equals(DeliveryStatus.CONFIRMADO)){
            throw new ReversedStatusException();
        }
        if (delivery.getStatus().equals(DeliveryStatus.TRANSITO) && status.equals(DeliveryStatus.PREPARANDO)){
            throw new ReversedStatusException();
        }
        try {
            delivery.setStatus(status);
            return repository.save(delivery);
        } catch (Exception e){
            throw new ErrorWhileSavingDeliveryException();
        }
    }
}
