package Alavarse.Ortega.Battery.Commerce.Repositories;

import Alavarse.Ortega.Battery.Commerce.Entities.SaleEntity;
import Alavarse.Ortega.Battery.Commerce.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<SaleEntity, String> {
    List<SaleEntity> findByUser(UserEntity user);

    @Query(value = "SELECT * FROM sale s WHERE s.creation_date < :start", nativeQuery = true)
    List<SaleEntity> findByDate(@Param("start") LocalDate start);

    @Query(value = "SELECT * FROM sale s WHERE s.value BETWEEN :start AND :end", nativeQuery = true)
    List<SaleEntity> findByPrice(@Param("start") int start, @Param("end") int end);

    @Query(value = "SELECT s.*, p.payment_id as payment_id_alias FROM public.sale s INNER JOIN payment p ON p.payment_id = s.payment_id WHERE p.status = 'PENDENTE' AND s.user_id = :userId", nativeQuery = true)
    List<SaleEntity> findNoPaymentSales(@Param("userId") String userId);

}
