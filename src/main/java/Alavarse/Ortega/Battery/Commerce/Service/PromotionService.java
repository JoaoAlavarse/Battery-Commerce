package Alavarse.Ortega.Battery.Commerce.Service;

import Alavarse.Ortega.Battery.Commerce.DTO.PromotionDTO;
import Alavarse.Ortega.Battery.Commerce.Entity.PromotionEntity;
import Alavarse.Ortega.Battery.Commerce.Entity.UserEntity;
import Alavarse.Ortega.Battery.Commerce.Enum.PromotionStatus;
import Alavarse.Ortega.Battery.Commerce.Repository.PromotionRepository;
import org.hibernate.jpa.boot.spi.Bootstrap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PromotionService {
    @Autowired
    private PromotionRepository repository;

    @Autowired
    private UserService userService;

    public PromotionEntity getByCode(String code){
        return repository.findByCode(code).orElseThrow(RuntimeException::new);
    }

    public PromotionEntity getById(String id){
        return repository.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<PromotionEntity> getAll(){
        return repository.findAllActive();
    }

    public PromotionEntity create(PromotionDTO data){
        return repository.save(new PromotionEntity(data.expirationDate(), data.percentage(), data.code()));
    }

    public void autoExpire(){
        List<PromotionEntity> list = repository.findExpiredPromotions();
        list.forEach(promotionEntity -> {
            promotionEntity.setStatus(PromotionStatus.EXPIRED);
            repository.save(promotionEntity);
        });
    }

    public BigDecimal getDiscountValue(String code, BigDecimal totalValue, String userId){
        PromotionEntity promotion = repository.findByCode(code).orElseThrow(RuntimeException::new);
        if (hasPromotionBeenUsed(userId, code)){
            throw new RuntimeException();
        }
        if (promotion.getStatus().equals(PromotionStatus.EXPIRED) || promotion.getStatus().equals(PromotionStatus.INACTIVE)){
            throw new RuntimeException();
        }
        BigDecimal percentage = BigDecimal.valueOf(promotion.getPercentage());
        BigDecimal multiplier = percentage.divide(BigDecimal.valueOf(100));
        BigDecimal discountAmount = totalValue.multiply(multiplier);
        return totalValue.subtract(discountAmount);
    }

    public PromotionEntity technicalDelete(String code){
        PromotionEntity promotion = repository.findByCode(code).orElseThrow(RuntimeException::new);
        promotion.setStatus(PromotionStatus.INACTIVE);
        return repository.save(promotion);
    }

    public boolean hasPromotionBeenUsed(String userId, String promotionCode){
        UserEntity user = userService.findById(userId);
        PromotionEntity promotion = getByCode(promotionCode);

        return user.getUsedPromotions().contains(promotion);
    }

}
