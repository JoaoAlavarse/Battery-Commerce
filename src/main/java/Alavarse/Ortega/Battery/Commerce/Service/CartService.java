package Alavarse.Ortega.Battery.Commerce.Service;

import Alavarse.Ortega.Battery.Commerce.DTO.DiscountDTO;
import Alavarse.Ortega.Battery.Commerce.DTO.PromotionDTO;
import Alavarse.Ortega.Battery.Commerce.Entity.BatteryEntity;
import Alavarse.Ortega.Battery.Commerce.Entity.CartEntity;
import Alavarse.Ortega.Battery.Commerce.Entity.PromotionEntity;
import Alavarse.Ortega.Battery.Commerce.Enum.CartStatus;
import Alavarse.Ortega.Battery.Commerce.Repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiFunction;

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
        CartEntity cart = repository.findById(id).orElseThrow(RuntimeException::new);
        BigDecimal newValue = this.getTotalValue(cart.getCartId());
        cart.setTotalValue(newValue);
        return repository.save(cart);
    }

    public List<CartEntity> getAll(){
        List<CartEntity> list = repository.findAll();
        list.forEach(cart -> {
            this.getTotalValue(cart.getCartId());
        });
        return list;
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

    public CartEntity getByUser(String userId){
        CartEntity cart = repository.findByUser(userService.findById(userId));
        BigDecimal newValue = this.getTotalValue(cart.getCartId());
        cart.setTotalValue(newValue);
        return repository.save(cart);
    }

    public BigDecimal getTotalValue(String id){
        BigDecimal batteryValues = BigDecimal.ZERO;
        CartEntity cart = this.getById(id);
        cart.getBatteries().forEach(battery -> {
            batteryValues.add(battery.getValue());
        });
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
