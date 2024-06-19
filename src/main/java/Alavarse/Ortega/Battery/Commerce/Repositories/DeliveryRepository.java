package Alavarse.Ortega.Battery.Commerce.Repositories;


import Alavarse.Ortega.Battery.Commerce.Entities.DeliveryEntity;
import Alavarse.Ortega.Battery.Commerce.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<DeliveryEntity, String> {
    List<DeliveryEntity> findByUser(UserEntity user);

    @Query(value = "SELECT * FROM delivery d WHERE d.status = :status", nativeQuery = true)
    List<DeliveryEntity> findByStatus(@Param("status") String status);

    @Query(value = "SELECT * FROM delivery d WHERE d.creation_date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL :start MONTH) AND CURRENT_DATE", nativeQuery = true)
    List<DeliveryEntity> findByDate(@Param("start") int start);

    @Query(value = "SELECT * FROM delivery d WHERE d.creation_date < DATE_SUB(CURRENT_DATE, INTERVAL 6 MONTH)", nativeQuery = true)
    List<DeliveryEntity> findByOverDate();

    @Query(value = "SELECT d.* FROM delivery d LEFT JOIN sale s ON s.sale_id = d.sale_id LEFT JOIN payment p ON p.payment_id = s.payment_id WHERE p.payment_id = :paymentId", nativeQuery = true)
    DeliveryEntity findByPaymentId(@Param("paymentId") String paymentId);

}
