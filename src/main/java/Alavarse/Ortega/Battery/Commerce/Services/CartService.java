package Alavarse.Ortega.Battery.Commerce.Services;

import Alavarse.Ortega.Battery.Commerce.DTOs.DiscountDTO;
import Alavarse.Ortega.Battery.Commerce.Entities.*;
import Alavarse.Ortega.Battery.Commerce.Enums.CartStatus;
import Alavarse.Ortega.Battery.Commerce.Repositories.CartBatteryRepository;
import Alavarse.Ortega.Battery.Commerce.Repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    @Autowired
    private CartBatteryRepository cartBatteryRepository;

    public CartEntity create(String userId){
        return repository.save(new CartEntity(userService.findById(userId)));
    }

    public CartEntity getById(String id){
        CartEntity cart = repository.findById(id).orElseThrow(RuntimeException::new);
        BigDecimal newValue = this.getTotalValue(cart.getCartId());
        cart.setTotalValue(newValue);
        return repository.save(cart);
    }

    public List<CartEntity> getAll(){
        List<CartEntity> list = repository.findAll();
        list.forEach(cart -> {
            cart.setTotalValue(this.getTotalValue(cart.getCartId()));
        });
        return list;
    }

    public void autoCancel(){
        List<CartEntity> list = this.getAll();
        List<UserEntity> userList = new ArrayList<>();
        list.forEach(cartEntity -> {
            cartEntity.setStatus(CartStatus.CANCELLED);
            userList.add(cartEntity.getUser());
            repository.save(cartEntity);
        });
        userList.forEach(userEntity -> {
            this.create(userEntity.getUserId());
        });
    }

    public CartEntity addBatteries(String id, String batteryId, int quantity){
        CartEntity cart = this.getById(id);
        BatteryEntity battery = batteryService.getById(batteryId);

        if (battery.getQuantity() < quantity){
            throw new RuntimeException("numero bateria");
        }

        boolean exists = cart.getBatteries().stream().anyMatch(cartBatteryEntity -> cartBatteryEntity.getBattery().getBatteryId().equals(batteryId));
        if (exists){
            throw new RuntimeException("Ja tem");
        }

        CartBatteryEntity cartBatteryEntity = new CartBatteryEntity(quantity, battery, cart);
        cartBatteryRepository.save(cartBatteryEntity);
        cart.getBatteries().add(cartBatteryEntity);
        cart.setTotalValue(this.getTotalValue(id));
        return repository.save(cart);
    }

    public CartEntity removeBatteries(String id, String batteryId){
        CartEntity cart = this.getById(id);
        BatteryEntity battery = batteryService.getById(batteryId);

        CartBatteryEntity cartBatteryToRemove = null;
        for (CartBatteryEntity cartBattery : cart.getBatteries()) {
            if (cartBattery.getBattery().getBatteryId().equals(batteryId)) {
                cartBatteryToRemove = cartBattery;
                break;
            }
        }

        if (cartBatteryToRemove != null) {
            cart.getBatteries().remove(cartBatteryToRemove);
        }

        cartBatteryRepository.delete(cartBatteryToRemove);
        cart.setTotalValue(this.getTotalValue(id));
        return repository.save(cart);
    }

    public CartEntity closeCart(String id){
        CartEntity cart = this.getById(id);
        cart.setStatus(CartStatus.CLOSED);
        return repository.save(cart);
    }

    public CartEntity getByUser(String userId){
        CartEntity cart = repository.findByUser(userService.findById(userId));
        BigDecimal newValue = this.getTotalValue(cart.getCartId());
        cart.setTotalValue(newValue);
        return repository.save(cart);
    }

    public BigDecimal getTotalValue(String id){
        BigDecimal batteryValues = BigDecimal.ZERO;
        CartEntity cart = repository.findById(id).orElseThrow(RuntimeException::new);;
        for (CartBatteryEntity cartBattery : cart.getBatteries()) {
            batteryValues = batteryValues.add(cartBattery.getBattery().getValue().multiply(BigDecimal.valueOf(cartBattery.getQuantity())));        }
        cart.setTotalValue(batteryValues);
        if (cart.getPromotion() == null){
            repository.save(cart);
            return cart.getTotalValue();
        }
        BigDecimal promotionValue = promotionService.getDiscountValue(cart.getPromotion().getCode(), new DiscountDTO(cart.getUser().getUserId(), cart.getTotalValue()));
        cart.setTotalValue(promotionValue);
        repository.save(cart);
        return cart.getTotalValue();
    }


    public CartEntity removePromotion(String id){
        CartEntity cart = this.getById(id);
        cart.setPromotion(null);
        this.getTotalValue(cart.getCartId());
        return repository.save(cart);
    }

    public CartEntity addPromotion(String id, String promotionCode){
        CartEntity cart = this.getById(id);
        PromotionEntity promotion = promotionService.getByCode(promotionCode);
        cart.setPromotion(promotion);
        this.getTotalValue(id);
        return repository.save(cart);
    }
}
