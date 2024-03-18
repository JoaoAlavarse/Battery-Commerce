package Alavarse.Ortega.Battery.Commerce.Repositories;

import Alavarse.Ortega.Battery.Commerce.Entities.CartEntity;
import Alavarse.Ortega.Battery.Commerce.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<CartEntity, String> {
    @Query(value = "SELECT * FROM cart c WHERE p.creation_date < CURRENT_DATE - INTERVAL '2 days'", nativeQuery = true)
    List<CartEntity> findByTwoDaysOfExistence();

    CartEntity findByUser(UserEntity user);
}
