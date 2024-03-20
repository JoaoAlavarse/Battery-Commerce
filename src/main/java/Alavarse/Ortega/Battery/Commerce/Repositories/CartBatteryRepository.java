package Alavarse.Ortega.Battery.Commerce.Repositories;

import Alavarse.Ortega.Battery.Commerce.Entities.CartBatteryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartBatteryRepository extends JpaRepository<CartBatteryEntity, String> {
}
