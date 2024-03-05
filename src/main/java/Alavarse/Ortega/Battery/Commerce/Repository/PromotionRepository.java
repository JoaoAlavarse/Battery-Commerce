package Alavarse.Ortega.Battery.Commerce.Repository;

import Alavarse.Ortega.Battery.Commerce.Entity.PromotionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<PromotionEntity, String> {
    Optional<PromotionEntity> findByCode(String code);

    @Query(value = "SELECT * FROM promotion p WHERE p.expiration_date < CURRENT_DATE", nativeQuery = true)
    List<PromotionEntity> findExpiredPromotions();

    @Query(value = "SELECT * FROM promotion p WHERE p.status = 'ACTIVE'", nativeQuery = true)
    List<PromotionEntity> findAllActive();
}
