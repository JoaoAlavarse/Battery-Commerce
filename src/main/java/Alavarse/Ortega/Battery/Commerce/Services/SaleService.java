package Alavarse.Ortega.Battery.Commerce.Services;

import Alavarse.Ortega.Battery.Commerce.DTOs.SaleDTO;
import Alavarse.Ortega.Battery.Commerce.Entities.*;
import Alavarse.Ortega.Battery.Commerce.Repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleService {
    @Autowired
    private SaleRepository repository;
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private DeliveryService deliveryService;

    public SaleEntity create(SaleDTO data){
        UserEntity user = userService.findById(data.userId());
        CartEntity cart = cartService.getById(data.cartId());
        AddressEntity address = addressService.getById(data.addressId());
        PromotionEntity promotion = null;
        if (!data.promotionId().isBlank()){
            promotion = promotionService.getById(data.promotionId());
        }
        SaleEntity sale =  this.repository.save(new SaleEntity(data.value(), data.freightValue(), user, cart, promotion));
        this.deliveryService.create(address.getAddress(), address.getNumber(), address.getNeighborhood(), address.getComplement(),
                address.getCity(), address.getCity(), address.getCEP(), sale, user);
        return sale;
    }

    public List<SaleEntity> getByUser(String userId){
        return this.repository.findByUser(this.userService.findById(userId));
    }

    public List<SaleEntity> getAll(){
        return this.repository.findAll();
    }
}