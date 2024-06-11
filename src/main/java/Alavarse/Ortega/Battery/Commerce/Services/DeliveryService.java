package Alavarse.Ortega.Battery.Commerce.Services;

import Alavarse.Ortega.Battery.Commerce.Entities.DeliveryEntity;
import Alavarse.Ortega.Battery.Commerce.Entities.SaleEntity;
import Alavarse.Ortega.Battery.Commerce.Repositories.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {
    @Autowired
    private DeliveryRepository repository;

    public void create(String address, String number, String neighborhood, String complement, String city, String state, String CEP, SaleEntity sale){
            this.repository.save(new DeliveryEntity(address, number, neighborhood, complement, city, state, CEP, sale));
    }
}
