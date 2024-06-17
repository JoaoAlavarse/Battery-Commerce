package Alavarse.Ortega.Battery.Commerce.Repositories;

import Alavarse.Ortega.Battery.Commerce.Entities.UtilsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilsRepository extends JpaRepository<UtilsEntity, Integer> {
    Optional<UtilsEntity> getByKey(String key);
}
