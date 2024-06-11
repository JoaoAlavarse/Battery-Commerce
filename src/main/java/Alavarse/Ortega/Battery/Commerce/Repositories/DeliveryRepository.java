package Alavarse.Ortega.Battery.Commerce.Repositories;


import Alavarse.Ortega.Battery.Commerce.Entities.DeliveryEntity;
import Alavarse.Ortega.Battery.Commerce.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<DeliveryEntity, String> {
    List<DeliveryEntity> findByUser(UserEntity user);
}
