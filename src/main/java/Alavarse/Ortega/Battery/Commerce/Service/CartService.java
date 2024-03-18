package Alavarse.Ortega.Battery.Commerce.Service;

import Alavarse.Ortega.Battery.Commerce.Entity.BatteryEntity;
import Alavarse.Ortega.Battery.Commerce.Entity.CartEntity;
import Alavarse.Ortega.Battery.Commerce.Enum.CartStatus;
import Alavarse.Ortega.Battery.Commerce.Repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartRepository repository;
    @Autowired
    private UserService userService;
    @Autowired
    private BatteryService batteryService;
    @Autowired
    private PromotionService promotionService;

    public CartEntity create(String userId){
        return repository.save(new CartEntity(userService.findById(userId)));
    }

    public CartEntity getById(String id){
        return repository.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<CartEntity> getAll(){
        return repository.findAll();
    }

    public void autoCancel(){
        List<CartEntity> list = this.getAll();
        list.forEach(cartEntity -> {
            cartEntity.setStatus(CartStatus.CANCELLED);
            repository.save(cartEntity);
        });
    }

    public CartEntity addBatteries(String id, String batteryId){
        CartEntity cart = this.getById(id);
        BatteryEntity battery = batteryService.getById(id);
        if (cart.getBatteries().contains(battery)){
            throw new RuntimeException();
        }
        cart.getBatteries().add(battery);
        return repository.save(cart);
    }

    public CartEntity removeBatteries(String id, String batteryId){
        CartEntity cart = this.getById(id);
        BatteryEntity battery = batteryService.getById(id);
        cart.getBatteries().remove(battery);
        return repository.save(cart);
    }

    public CartEntity closeCart(String id){
        CartEntity cart = this.getById(id);
        cart.setStatus(CartStatus.CLOSED);
        return repository.save(cart);
    }
}
