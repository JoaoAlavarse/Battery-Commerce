package Alavarse.Ortega.Battery.Commerce.Repositories;

import Alavarse.Ortega.Battery.Commerce.Entities.CartEntity;
import Alavarse.Ortega.Battery.Commerce.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<CartEntity, String> {
    @Query(value = "SELECT * FROM cart c WHERE c.creation_date < CURRENT_DATE - INTERVAL '2 days' AND c.status = 'OPENED'", nativeQuery = true)
    List<CartEntity> findByTwoDaysOfExistence();

    @Query(value = "SELECT * FROM cart c WHERE c.status = 'OPENED' AND c.user_id = :userId", nativeQuery = true)
    CartEntity findByUser(@Param("userId") String userId);
}
