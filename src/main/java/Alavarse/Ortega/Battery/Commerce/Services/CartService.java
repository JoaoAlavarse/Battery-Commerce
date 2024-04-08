package Alavarse.Ortega.Battery.Commerce.Services;

import Alavarse.Ortega.Battery.Commerce.DTOs.DiscountDTO;
import Alavarse.Ortega.Battery.Commerce.Entities.*;
import Alavarse.Ortega.Battery.Commerce.Enums.CartStatus;
import Alavarse.Ortega.Battery.Commerce.Exceptions.CartExceptions.*;
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
        try {
            return repository.save(new CartEntity(userService.findById(userId)));
        } catch (Exception e){
            throw new ErrorWhileSavingCartException();
        }
    }

    public CartEntity getById(String id){
        CartEntity cart = repository.findById(id).orElseThrow(CartNotFoundException::new);
        try {
            BigDecimal newValue = this.getTotalValue(cart.getCartId());
            cart.setTotalValue(newValue);
            return repository.save(cart);
        } catch (Exception e){
            throw new ErrorWhileSavingCartException();
        }
    }

    public List<CartEntity> getAll(){
        try {
            List<CartEntity> list = repository.findAll();
            list.forEach(cart -> {
                cart.setTotalValue(this.getTotalValue(cart.getCartId()));
            });
            return list;
        } catch (Exception e){
            throw new ErrorWhileGettingCartException();
        }
    }

    public void autoCancel(){
        List<UserEntity> userList = new ArrayList<>();
        for (CartEntity cartEntity : repository.findByTwoDaysOfExistence()){
            if (cartEntity.getBatteries().isEmpty()){
                this.create(cartEntity.getUser().getUserId());
                repository.delete(cartEntity);
                continue;
            }
            userList.add(cartEntity.getUser());
            cartEntity.setStatus(CartStatus.CANCELLED);
            repository.save(cartEntity);
        }
        userList.forEach(userEntity -> this.create(userEntity.getUserId()));
    }

    public CartEntity addBatteries(String id, String batteryId, int quantity){
        CartEntity cart = this.getById(id);
        BatteryEntity battery = batteryService.getById(batteryId);

        if (battery.getQuantity() < quantity){
            throw new InsufficientBatteriesToAddException();
        }

        boolean exists = cart.getBatteries().stream().anyMatch(cartBatteryEntity -> cartBatteryEntity.getBattery().getBatteryId().equals(batteryId));
        if (exists){
            throw new BatteryAlreadyExistsInException();
        }

        try {
            CartBatteryEntity cartBatteryEntity = new CartBatteryEntity(quantity, battery, cart);
            cartBatteryRepository.save(cartBatteryEntity);
            cart.getBatteries().add(cartBatteryEntity);
            cart.setTotalValue(this.getTotalValue(id));
            return repository.save(cart);
        } catch (Exception e){
            throw new ErrorWhileSavingCartException();
        }
    }

    public CartEntity removeBatteries(String id, String batteryId){
        CartEntity cart = this.getById(id);
        BatteryEntity battery = batteryService.getById(batteryId);

        for (CartBatteryEntity cartBattery : cart.getBatteries()) {
            if (cartBattery.getBattery().getBatteryId().equals(batteryId)) {
                cart.getBatteries().remove(cartBattery);
                cartBatteryRepository.delete(cartBattery);
                break;
            }
            throw new BatteryDoenstExistsInException();
        }

        cart.setTotalValue(this.getTotalValue(id));
        try {
            return repository.save(cart);
        } catch (Exception e){
            throw new ErrorWhileSavingCartException();
        }
    }

    public CartEntity closeCart(String id){
        CartEntity cart = this.getById(id);
        cart.setStatus(CartStatus.CLOSED);
        return repository.save(cart);
    }

    public CartEntity getByUser(String userId){
        try {
            CartEntity cart = repository.findByUser(userService.findById(userId));
            BigDecimal newValue = this.getTotalValue(cart.getCartId());
            cart.setTotalValue(newValue);
            return repository.save(cart);
        } catch (Exception e){
            throw new ErrorWhileGettingCartException();
        }
    }

    public BigDecimal getTotalValue(String id){
        try {
            BigDecimal batteryValues = BigDecimal.ZERO;
            CartEntity cart = repository.findById(id).orElseThrow(CartNotFoundException::new);
            ;
            for (CartBatteryEntity cartBattery : cart.getBatteries()) {
                batteryValues = batteryValues.add(cartBattery.getBattery().getValue().multiply(BigDecimal.valueOf(cartBattery.getQuantity())));
            }
            cart.setTotalValue(batteryValues);
            if (cart.getPromotion() == null) {
                repository.save(cart);
                return cart.getTotalValue();
            }
            BigDecimal promotionValue = promotionService.getDiscountValue(cart.getPromotion().getCode(), new DiscountDTO(cart.getUser().getUserId(), cart.getTotalValue()));
            cart.setTotalValue(promotionValue);
            repository.save(cart);
            return cart.getTotalValue();
        } catch (Exception e){
            throw new ErrorWhileCalculatingCartTotalValueException();
        }
    }


    public CartEntity removePromotion(String id){
        try {
            CartEntity cart = this.getById(id);
            cart.setPromotion(null);
            this.getTotalValue(cart.getCartId());
            return repository.save(cart);
        } catch (Exception e){
            throw new ErrorWhileRemovingPromotionException();
        }
    }

    public CartEntity addPromotion(String id, String promotionCode){
        try {
            CartEntity cart = this.getById(id);
            PromotionEntity promotion = promotionService.getByCode(promotionCode);
            cart.setPromotion(promotion);
            this.getTotalValue(id);
            return repository.save(cart);
        } catch (Exception e){
            throw new ErrorWhileAddingPromotionException();
        }
    }
}
