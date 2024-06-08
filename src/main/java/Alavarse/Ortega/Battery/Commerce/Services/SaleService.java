package Alavarse.Ortega.Battery.Commerce.Services;

import Alavarse.Ortega.Battery.Commerce.DTOs.SaleDTO;
import Alavarse.Ortega.Battery.Commerce.Entities.AddressEntity;
import Alavarse.Ortega.Battery.Commerce.Entities.CartEntity;
import Alavarse.Ortega.Battery.Commerce.Entities.SaleEntity;
import Alavarse.Ortega.Battery.Commerce.Entities.UserEntity;
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

    public SaleEntity create(SaleDTO data){
        UserEntity user = userService.findById(data.userId());
        CartEntity cart = cartService.getById(data.cartId());
        AddressEntity address = addressService.getById(data.addressId());
        return this.repository.save(new SaleEntity(user, cart, data.value(), data.freightValue(), address.getAddress(), address.getNumber(),
                address.getNeighborhood(), address.getComplement(), address.getCity(), address.getState(), address.getCEP()));
    }

    public List<SaleEntity> getByUser(String userId){
        return this.repository.findByUser(this.userService.findById(userId));
    }
}
