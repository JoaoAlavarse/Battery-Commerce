package Alavarse.Ortega.Battery.Commerce.Repositories;

import Alavarse.Ortega.Battery.Commerce.Entities.BatteryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BatteryRepository extends JpaRepository<BatteryEntity, String> {
    @Query(value = "SELECT * FROM battery b WHERE b.status = 'ACTIVE'", nativeQuery = true)
    List<BatteryEntity> findAllActive();

    Optional<BatteryEntity> findByCode(String code);
}
