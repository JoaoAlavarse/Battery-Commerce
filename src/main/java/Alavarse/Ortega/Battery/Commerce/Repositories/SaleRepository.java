package Alavarse.Ortega.Battery.Commerce.Repositories;

import Alavarse.Ortega.Battery.Commerce.Entities.SaleEntity;
import Alavarse.Ortega.Battery.Commerce.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SaleRepository extends JpaRepository<SaleEntity, String> {
    List<SaleEntity> findByUser(UserEntity user);

    @Query(value = "SELECT * FROM sale s WHERE s.creation_date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL :start MONTH) AND CURRENT_DATE", nativeQuery = true)
    List<SaleEntity> findByDate(@Param("start") int start);

    @Query(value = "SELECT * FROM sale s WHERE s.creation_date < DATE_SUB(CURRENT_DATE, INTERVAL 6 MONTH)", nativeQuery = true)
    List<SaleEntity> findByOverDate();

    @Query(value = "SELECT * FROM sale s WHERE s.value BETWEEN :start AND :end", nativeQuery = true)
    List<SaleEntity> findByPrice(@Param("start") int start, @Param("end") int end);
}
