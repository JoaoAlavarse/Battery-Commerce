package Alavarse.Ortega.Battery.Commerce.Repository;

import Alavarse.Ortega.Battery.Commerce.Entity.BatteryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatteryRepository extends JpaRepository<BatteryEntity, String> {
    @Query(value = "SELECT * FROM battery b WHERE b.status = 'ACTIVE'", nativeQuery = true)
    List<BatteryEntity> findAllActive();
}
