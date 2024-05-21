package Alavarse.Ortega.Battery.Commerce.Repositories;

import Alavarse.Ortega.Battery.Commerce.Entities.PromotionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query(value = "SELECT * FROM promotion p WHERE p.status = 'INACTIVE'", nativeQuery = true)
    List<PromotionEntity> findAllInactive();

    @Query(value = "SELECT * FROM promotion p WHERE p.percentage BETWEEN :start AND :end", nativeQuery = true)
    List<PromotionEntity> findByPercentage(@Param("start") int start, @Param("end") int end);

    @Query(value = "SELECT * FROM promotion p WHERE p.expiration_date BETWEEN CURRENT_DATE AND (CURRENT_DATE + :end * INTERVAL '1 month')", nativeQuery = true)    List<PromotionEntity> findByDate(@Param("end") int end);

    @Query(value = "SELECT * FROM promotion p WHERE p.expiration_date > CURRENT_DATE", nativeQuery = true)
    List<PromotionEntity> findByOverDate();
}
