package Alavarse.Ortega.Battery.Commerce.Services;

import Alavarse.Ortega.Battery.Commerce.DTOs.Freight.FreightResponseDTO;
import Alavarse.Ortega.Battery.Commerce.DTOs.Sale.SaleDTO;
import Alavarse.Ortega.Battery.Commerce.Entities.*;
import Alavarse.Ortega.Battery.Commerce.Exceptions.NoSuchReportTypeException;
import Alavarse.Ortega.Battery.Commerce.Exceptions.SaleExceptions.ErrorWhileGettingSaleException;
import Alavarse.Ortega.Battery.Commerce.Exceptions.SaleExceptions.ErrorWhileSavingSaleException;
import Alavarse.Ortega.Battery.Commerce.Repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    @Autowired
    private FreightService freightService;

    public SaleEntity create(SaleDTO data, PaymentEntity payment){
        UserEntity user = userService.findById(data.userId());
        CartEntity cart = cartService.getById(data.cartId());
        AddressEntity address = addressService.getById(data.addressId());
        FreightResponseDTO freight = this.freightService.getFreightInfo(data.cep(), cartService.getTotalItems(cart));
        PromotionEntity promotion = null;
        if (data.promotionId() != null && !data.promotionId().isBlank()){
            promotion = promotionService.getById(data.promotionId());
            promotionService.hasPromotionBeenUsed(user.getUserId(), promotion.getCode());
        }

        SaleEntity sale = null;

        try {
            sale = this.repository.save(new SaleEntity(cart.getTotalValue().add(freight.totalFreightCost()), freight.totalFreightCost(), user, cart, promotion, payment));
            this.cartService.closeCart(cart.getCartId());
        } catch (Exception e){
            throw new ErrorWhileSavingSaleException();
        }

        this.deliveryService.create(address.getAddress(), address.getNumber(), address.getNeighborhood(), address.getComplement(),
                address.getCity(), address.getState(), address.getCEP(), sale, user);
        return sale;
    }

    public List<SaleEntity> getByUser(String userId){
        try {
            List<SaleEntity> list = this.repository.findByUser(this.userService.findById(userId));
            if (!list.isEmpty()) {
                for (SaleEntity sale : list) {
                    sale.setValue(sale.getCart().getTotalValue());
                    repository.save(sale);
                }
            }
            return list;
        } catch (Exception e){
            throw new ErrorWhileGettingSaleException();
        }
    }

    public List<SaleEntity> getAll(){
        try {
            List<SaleEntity> list = this.repository.findAll();
            if (!list.isEmpty()) {
                for (SaleEntity sale : list) {
                    sale.setValue(sale.getCart().getTotalValue());
                    repository.save(sale);
                }
            }
            return list;
        } catch (Exception e){
            throw new ErrorWhileGettingSaleException();
        }
    }

    public List<SaleEntity> getNoPaymentSales(String userId){
        try{
            List<SaleEntity> list = this.repository.findNoPaymentSales(userId);
            if (!list.isEmpty()) {
                for (SaleEntity sale : list) {
                    sale.setValue(sale.getCart().getTotalValue());
                    repository.save(sale);
                }
            }
            return list;
        } catch (Exception e){
            e.printStackTrace();
            throw new ErrorWhileGettingSaleException();
        }
    }

    public List<SaleEntity> getReportData(String report){
        return switch (report) {
            case "sale-value-250" -> this.repository.findByPrice(0, 250);
            case "sale-value-500" -> this.repository.findByPrice(250, 500);
            case "sale-value-1000" -> this.repository.findByPrice(500, 1000);
            case "sale-value-over-1000" -> this.repository.findByPrice(1000, 10000);
            case "sale-creation-1" -> this.repository.findByDate(LocalDate.now().minusMonths(1));
            case "sale-creation-3" -> this.repository.findByDate(LocalDate.now().minusMonths(3));
            case "sale-creation-6" -> this.repository.findByDate(LocalDate.now().minusMonths(6));
            case "sale-creation-over-6" -> this.repository.findByDate(LocalDate.now().minusMonths(7));
            case "sale-clear" -> this.repository.findAll();
            default -> throw new NoSuchReportTypeException();
        };
    }
}
