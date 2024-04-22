package Alavarse.Ortega.Battery.Commerce.Services;

import Alavarse.Ortega.Battery.Commerce.DTOs.DiscountDTO;
import Alavarse.Ortega.Battery.Commerce.DTOs.PromotionDTO;
import Alavarse.Ortega.Battery.Commerce.Entities.PromotionEntity;
import Alavarse.Ortega.Battery.Commerce.Entities.UserEntity;
import Alavarse.Ortega.Battery.Commerce.Enums.PromotionStatus;
import Alavarse.Ortega.Battery.Commerce.Exceptions.PromotionExceptions.*;
import Alavarse.Ortega.Battery.Commerce.Repositories.PromotionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class PromotionService {
    @Autowired
    private PromotionRepository repository;

    @Autowired
    private UserService userService;

    public PromotionEntity getByCode(String code){
        return repository.findByCode(code).orElseThrow(PromotionNotFoundException::new);
    }

    public PromotionEntity getById(String id){
        return repository.findById(id).orElseThrow(PromotionNotFoundException::new);
    }

    public List<PromotionEntity> getAll(){
        try {
            return repository.findAll();
        } catch (Exception e){
            throw new ErrorWhileGettingPromotionException();
        }
    }

    public PromotionEntity create(PromotionDTO data){
        if (repository.findByCode(data.code()).isPresent()){
            throw new PromotionAlreadyExists();
        }
        if (LocalDate.now().isAfter(data.expirationDate())){
            throw new InvalidExpirationDateException();
        }
        try {
            return repository.save(new PromotionEntity(data.expirationDate(), data.percentage(), data.code()));
        } catch (Exception e){
            throw new ErrorWhileSavingPromotionException();
        }
    }

    public void autoExpire(){
        List<PromotionEntity> list = repository.findExpiredPromotions();
        list.forEach(promotionEntity -> {
            promotionEntity.setStatus(PromotionStatus.EXPIRED);
            repository.save(promotionEntity);
        });
    }

    public BigDecimal getDiscountValue(String code, DiscountDTO data){
        PromotionEntity promotion = repository.findByCode(code).orElseThrow(PromotionNotFoundException::new);
        if (hasPromotionBeenUsed(data.userId(), code)){
            throw new PromotionAlreadyBeenUsedException();
        }
        if (promotion.getStatus().equals(PromotionStatus.EXPIRED) || promotion.getStatus().equals(PromotionStatus.INACTIVE)){
            throw new InvalidPromotionException();
        }
        try {
            BigDecimal percentage = BigDecimal.valueOf(promotion.getPercentage());
            BigDecimal multiplier = percentage.divide(BigDecimal.valueOf(100));
            BigDecimal discountAmount = data.totalValue().multiply(multiplier);
            return data.totalValue().subtract(discountAmount);
        } catch (Exception e){
            throw new ErrorWhileSavingPromotionException();
        }
    }

    public PromotionEntity technicalDelete(String code){
        try {
            PromotionEntity promotion = repository.findByCode(code).orElseThrow(PromotionNotFoundException::new);
            promotion.setStatus(PromotionStatus.INACTIVE);
            return repository.save(promotion);
        } catch (Exception e){
            throw new ErrorWhileSavingPromotionException();
        }
    }

    public boolean hasPromotionBeenUsed(String userId, String promotionCode){
        UserEntity user = userService.findById(userId);
        PromotionEntity promotion = getByCode(promotionCode);
        return user.getUsedPromotions().contains(promotion);
    }

    public boolean setPromotionUsed(String userId, String promotionCode){
        UserEntity user = userService.findById(userId);
        PromotionEntity promotion = getByCode(promotionCode);
        return user.getUsedPromotions().add(promotion);
    }

    public PromotionEntity reactivePromotion(String code, PromotionDTO data){
        try {
            PromotionEntity promotion = this.getByCode(code);
            if (!promotion.getStatus().equals(PromotionStatus.INACTIVE)){
                throw new ErrorWhileSavingPromotionException();
            }
            if (data.expirationDate().isBefore(promotion.getExpirationDate())) {
                throw new InvalidExpirationDateException();
            }
            promotion.setExpirationDate(data.expirationDate());
            promotion.setStatus(PromotionStatus.ACTIVE);
            return repository.save(promotion);
        } catch (Exception e){
            throw new ErrorWhileSavingPromotionException();
        }
    }

    public PromotionEntity patchUpdate(String id, PromotionDTO data){
        try {
            PromotionEntity promotion = this.getById(id);
            if (promotion.getStatus().equals(PromotionStatus.INACTIVE)){
                throw new ErrorWhileSavingPromotionException();
            }
            if (promotion.getExpirationDate().isAfter(data.expirationDate())){
                throw new InvalidExpirationDateException();
            }
            BeanUtils.copyProperties(data, promotion, "promotionId");
            promotion.setStatus(PromotionStatus.ACTIVE);
            return repository.save(promotion);
        } catch (Exception e){
            throw new ErrorWhileSavingPromotionException();
        }
    }
}
