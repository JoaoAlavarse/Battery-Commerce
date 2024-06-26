package Alavarse.Ortega.Battery.Commerce.Services;

import Alavarse.Ortega.Battery.Commerce.DTOs.Promotion.DiscountDTO;
import Alavarse.Ortega.Battery.Commerce.Entities.*;
import Alavarse.Ortega.Battery.Commerce.Enums.CartStatus;
import Alavarse.Ortega.Battery.Commerce.Enums.PromotionStatus;
import Alavarse.Ortega.Battery.Commerce.Exceptions.CartExceptions.*;
import Alavarse.Ortega.Battery.Commerce.Exceptions.PromotionExceptions.InvalidPromotionException;
import Alavarse.Ortega.Battery.Commerce.Repositories.CartBatteryRepository;
import Alavarse.Ortega.Battery.Commerce.Repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
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

    public CartEntity create(String userId) {
        try {
            return repository.save(new CartEntity(userService.findById(userId)));
        } catch (Exception e) {
            throw new ErrorWhileSavingCartException();
        }
    }

    public CartEntity getById(String id) {
        CartEntity cart = repository.findById(id).orElseThrow(CartNotFoundException::new);
        try {
            BigDecimal newValue = this.getTotalValue(cart.getCartId());
            cart.setTotalValue(newValue);
            cart.setItemsQuantity(this.getTotalItems(cart));
            return repository.save(cart);
        } catch (Exception e) {
            throw new ErrorWhileSavingCartException();
        }
    }

    public List<CartEntity> getAll() {
        try {
            List<CartEntity> list = repository.findAll();
            list.forEach(cart -> {
                cart.setTotalValue(this.getTotalValue(cart.getCartId()));
                cart.setItemsQuantity(this.getTotalItems(cart));
            });
            return list;
        } catch (Exception e) {
            throw new ErrorWhileGettingCartException();
        }
    }

    public void autoCancel() {
        List<UserEntity> userList = new ArrayList<>();
        for (CartEntity cartEntity : repository.findByTwoDaysOfExistence()) {
            if (cartEntity.getBatteries().isEmpty()) {
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

    public CartEntity addBatteries(String id, String batteryId, int quantity) {
        CartEntity cart = this.getById(id);
        BatteryEntity battery = batteryService.getById(batteryId);

        if (battery.getQuantity() < quantity) {
            throw new InsufficientBatteriesException(battery.getQuantity(), battery.getName());
        }

        boolean exists = cart.getBatteries().stream().anyMatch(cartBatteryEntity -> cartBatteryEntity.getBattery().getBatteryId().equals(batteryId));
        if (exists) {
            throw new BatteryAlreadyExistsInException();
        }

        try {
            CartBatteryEntity cartBatteryEntity = new CartBatteryEntity(quantity, battery, cart);
            cartBatteryRepository.save(cartBatteryEntity);
            cart.getBatteries().add(cartBatteryEntity);
            cart.setTotalValue(this.getTotalValue(id));
            cart.setItemsQuantity(this.getTotalItems(cart));
            return repository.save(cart);
        } catch (Exception e) {
            throw new ErrorWhileSavingCartException();
        }
    }

    public CartEntity changeBatteryQuantity(String cartId, String cartBatteryEntityId, Integer quantity) {
        CartBatteryEntity cartBatteryEntity = cartBatteryRepository.findById(cartBatteryEntityId).orElseThrow(RuntimeException::new);
        BatteryEntity battery = cartBatteryEntity.getBattery();

        if (battery.getQuantity() < quantity) {
            throw new InsufficientBatteriesException(battery.getQuantity(), battery.getName());
        }

        cartBatteryEntity.setQuantity(quantity);
        cartBatteryRepository.save(cartBatteryEntity);

        CartEntity cart = cartBatteryEntity.getCart();
        cart.setTotalValue(this.getTotalValue(cartId));
        cart.setItemsQuantity(this.getTotalItems(cart));

        return repository.save(cart);
    }


    public CartEntity removeBatteries(String id, String batteryId) {
        CartEntity cart = this.getById(id);
        boolean batteryFound = false;

        Iterator<CartBatteryEntity> iterator = cart.getBatteries().iterator();
        while (iterator.hasNext()) {
            CartBatteryEntity cartBattery = iterator.next();
            if (cartBattery.getBattery().getBatteryId().equals(batteryId)) {
                iterator.remove();
                cartBatteryRepository.delete(cartBattery);
                cart.getBatteries().remove(cartBattery);
                batteryFound = true;
                break;
            }
        }

        if (!batteryFound) {
            throw new BatteryDoenstExistsInException();
        }

        cart.setTotalValue(this.getTotalValue(id));
        cart.setItemsQuantity(this.getTotalItems(cart));
        try {
            return repository.save(cart);
        } catch (Exception e) {
            throw new ErrorWhileSavingCartException();
        }
    }

    public void closeCart(String id) {
        CartEntity cart = this.getById(id);
        cart.setStatus(CartStatus.CLOSED);
        this.repository.save(cart);
        this.repository.save(new CartEntity(cart.getUser()));
    }

    public CartEntity getByUser(String userId) {
        CartEntity cart = repository.findByUser(userId);
        try {
            BigDecimal newValue = this.getTotalValue(cart.getCartId());
            cart.setTotalValue(newValue);
            cart.setItemsQuantity(this.getTotalItems(cart));
            return repository.save(cart);
        } catch (Exception e) {
            throw new ErrorWhileGettingCartException();
        }
    }

    public BigDecimal getTotalValue(String id) {
        try {
            BigDecimal batteryValues = BigDecimal.ZERO;
            CartEntity cart = repository.findById(id).orElseThrow(CartNotFoundException::new);
            for (CartBatteryEntity cartBattery : cart.getBatteries()) {
                batteryValues = batteryValues.add(cartBattery.getBattery().getValue().multiply(BigDecimal.valueOf(cartBattery.getQuantity())));
            }
            cart.setTotalValue(batteryValues);

            if (cart.getPromotion() != null && (cart.getPromotion().getStatus().equals(PromotionStatus.EXPIRED) || cart.getPromotion().getStatus().equals(PromotionStatus.INACTIVE))) {
                cart.setPromotion(null);
            }

            if (cart.getPromotion() == null) {
                cart.setItemsQuantity(this.getTotalItems(cart));
                repository.save(cart);
                return cart.getTotalValue();
            }

            if (cart.getPromotion().getStatus().equals(PromotionStatus.ACTIVE) && !cart.getStatus().equals(CartStatus.CLOSED)) {
                BigDecimal promotionValue = promotionService.getDiscountValue(cart.getPromotion().getCode(), new DiscountDTO(cart.getUser().getUserId(), cart.getTotalValue()));
                cart.setTotalValue(promotionValue);
            }

            cart.setItemsQuantity(this.getTotalItems(cart));
            repository.save(cart);
            return cart.getTotalValue();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorWhileCalculatingCartTotalValueException();
        }
    }


    public CartEntity removePromotion(String id) {
        try {
            CartEntity cart = this.getById(id);
            cart.setPromotion(null);
            this.getTotalValue(cart.getCartId());
            return repository.save(cart);
        } catch (Exception e) {
            throw new ErrorWhileRemovingPromotionException();
        }
    }

    public CartEntity addPromotion(String id, String promotionCode) {
        CartEntity cart = this.getById(id);
        PromotionEntity promotion = promotionService.getByCode(promotionCode);
        if (!promotion.getStatus().equals(PromotionStatus.ACTIVE)){
            throw new InvalidPromotionException();
        }
        this.promotionService.hasPromotionBeenUsed(cart.getUser().getUserId(), promotionCode);
        try {
            cart.setPromotion(promotion);
            this.getTotalValue(id);
            return repository.save(cart);
        } catch (Exception e) {
            throw new ErrorWhileAddingPromotionException();
        }
    }

    public Integer getTotalItems(CartEntity cart) {
        Integer totalItems = 0;
        for (CartBatteryEntity cartBattery : cart.getBatteries()) {
            totalItems += cartBattery.getQuantity();
        }
        return totalItems;
    }
}
